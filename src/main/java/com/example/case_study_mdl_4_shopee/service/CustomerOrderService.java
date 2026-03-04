package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.*;
import com.example.case_study_mdl_4_shopee.enums.OrderStatus;
import com.example.case_study_mdl_4_shopee.enums.SubOrderStatus;
import com.example.case_study_mdl_4_shopee.enums.TransactionType;
import com.example.case_study_mdl_4_shopee.repository.*;
import com.example.case_study_mdl_4_shopee.service.impl.ICustomerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerOrderService implements ICustomerOrderService {

    private final IOrdersRepository ordersRepository;
    private final IAccountRepository accountRepository;
    private final ISubOrdersRepository subOrdersRepository;
    private final ITransactionHistoryRepository transactionRepository;

    private final IProductRepository productRepository;
    private final IOrderItemsRepository orderItemsRepository;

    @Override
    @Transactional
    public Orders checkout(Long customerId, String paymentMethod, Long productId, Integer quantity) {
        Orders order;
        if (productId != null && quantity != null) {
            // Trường hợp mua ngay: tạo đơn hàng mới không liên quan đến giỏ hàng
            Product product = productRepository.findById(productId).orElseThrow();
            if (product.getStock() < quantity) {
                throw new RuntimeException("Số lượng hàng trong kho không đủ");
            }
            
            Account customer = accountRepository.findById(customerId).orElseThrow();
            
            order = Orders.builder()
                    .customerOrder(customer)
                    .orderStatus(OrderStatus.COMPLETED)
                    .total(product.getPrice().intValue() * quantity)
                    .build();
            ordersRepository.save(order);
            
            SubOrders subOrder = SubOrders.builder()
                    .order(order)
                    .sellerOrder(product.getSeller())
                    .total((long) product.getPrice() * quantity)
                    .status(SubOrderStatus.PENDING)
                    .build();
            subOrdersRepository.save(subOrder);
            
            OrderItems item = OrderItems.builder()
                    .subOrders(subOrder)
                    .product(product)
                    .quantity(quantity)
                    .price(product.getPrice().intValue())
                    .build();
            orderItemsRepository.save(item);

            // Trừ kho và tăng số lượng đã bán
            product.setStock(product.getStock() - quantity);
            Long currentSold = product.getSold() != null ? product.getSold() : 0L;
            product.setSold(currentSold + quantity);
            productRepository.save(product);

        } else {
            // Trường hợp thanh toán từ giỏ hàng
            order = ordersRepository.findByCustomerOrder_AccountIdAndOrderStatus(customerId, OrderStatus.IN_PROGRESS)
                    .orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại"));

            // Kiểm tra kho và trừ kho cho từng sản phẩm trong giỏ hàng (chỉ những item được chọn)
            for (SubOrders subOrder : order.getSubOrders()) {
                for (OrderItems item : subOrder.getOrderItems()) {
                    if (item.isChosen()) {
                        Product product = item.getProduct();
                        if (product.getStock() < item.getQuantity()) {
                            throw new RuntimeException("Sản phẩm " + product.getName() + " không đủ hàng trong kho");
                        }
                        product.setStock(product.getStock() - item.getQuantity());
                        Long currentSold = product.getSold() != null ? product.getSold() : 0L;
                        product.setSold(currentSold + item.getQuantity());
                        productRepository.save(product);
                    }
                }
            }

            // Cập nhật trạng thái đơn hàng và các đơn hàng con
            order.setOrderStatus(OrderStatus.COMPLETED);
            ordersRepository.save(order);

            for (SubOrders subOrder : order.getSubOrders()) {
                subOrder.setStatus(SubOrderStatus.PENDING);
                subOrdersRepository.save(subOrder);
            }
        }

        Account customer = order.getCustomerOrder();
        
        if ("SHOPEE_WALLET".equals(paymentMethod)) {
            if (customer.getBalance() < order.getTotal()) {
                throw new RuntimeException("Số dư ví Shopee không đủ");
            }

            // Trừ tiền khách hàng
            customer.setBalance(customer.getBalance() - order.getTotal());
            accountRepository.save(customer);

            // Ghi log giao dịch cho khách hàng
            transactionRepository.save(TransactionHistory.builder()
                    .accountTransaction(customer)
                    .amount((long) -order.getTotal())
                    .type(TransactionType.PAYMENT)
                    .description("Thanh toán đơn hàng #" + order.getOrdersId() + " qua ví Shopee")
                    .balanceAfter(customer.getBalance())
                    .build());
            
            // Cộng tiền cho các seller
            for (SubOrders subOrder : order.getSubOrders()) {
                Account seller = subOrder.getSellerOrder();
                seller.setBalance(seller.getBalance() + subOrder.getTotal());
                accountRepository.save(seller);

                // Ghi log giao dịch cho seller
                transactionRepository.save(TransactionHistory.builder()
                        .accountTransaction(seller)
                        .amount(subOrder.getTotal())
                        .type(TransactionType.PAYMENT)
                        .description("Nhận tiền từ đơn hàng con #" + subOrder.getSubOrderId())
                        .balanceAfter(seller.getBalance())
                        .build());
            }
        }
        return order;
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Orders order = ordersRepository.findById(orderId).orElseThrow();
        
        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Đơn hàng đã bị hủy trước đó");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        ordersRepository.save(order);

        for (SubOrders subOrder : order.getSubOrders()) {
            subOrder.setStatus(SubOrderStatus.CANCELLED);
            subOrdersRepository.save(subOrder);
            
            // Trừ tiền của seller (vì đã cộng ở bước checkout)
            Account seller = subOrder.getSellerOrder();
            seller.setBalance(seller.getBalance() - subOrder.getTotal());
            accountRepository.save(seller);

            // Ghi log hoàn tiền cho seller (số âm)
            transactionRepository.save(TransactionHistory.builder()
                    .accountTransaction(seller)
                    .amount(-subOrder.getTotal())
                    .type(TransactionType.REFUND)
                    .description("Hoàn trả tiền do đơn hàng #" + order.getOrdersId() + " bị hủy")
                    .balanceAfter(seller.getBalance())
                    .build());
        }
        
        // Hoàn tiền cho khách hàng
        Account customer = order.getCustomerOrder();
        customer.setBalance(customer.getBalance() + order.getTotal());
        accountRepository.save(customer);

        // Ghi log hoàn tiền cho khách hàng
        transactionRepository.save(TransactionHistory.builder()
                .accountTransaction(customer)
                .amount((long) order.getTotal())
                .type(TransactionType.REFUND)
                .description("Hoàn tiền đơn hàng #" + order.getOrdersId())
                .balanceAfter(customer.getBalance())
                .build());
    }

    @Override
    public List<Orders> viewOrderHistory(Long customerId) {
        // Cần thêm method trong repository hoặc lọc từ list
        // Tạm thời trả về tất cả đơn hàng của khách hàng
        return accountRepository.findById(customerId).orElseThrow().getOrders();
    }
}
