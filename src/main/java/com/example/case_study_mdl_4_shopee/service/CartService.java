package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.OrderItems;
import com.example.case_study_mdl_4_shopee.entity.Orders;
import com.example.case_study_mdl_4_shopee.entity.Product;
import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import com.example.case_study_mdl_4_shopee.enums.OrderStatus;
import com.example.case_study_mdl_4_shopee.repository.*;
import com.example.case_study_mdl_4_shopee.service.impl.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final IOrdersRepository ordersRepository;
    private final IOrderItemsRepository orderItemsRepository;
    private final IProductRepository productRepository;
    private final IAccountRepository accountRepository;
    private final ISubOrdersRepository subOrdersRepository;

    @Override
    public Orders getCart(Long customerId) {
        return ordersRepository.findByCustomerOrder_AccountIdAndOrderStatus(customerId, OrderStatus.IN_PROGRESS)
                .orElseGet(() -> {
                    Orders newCart = Orders.builder()
                            .customerOrder(accountRepository.findById(customerId).orElseThrow())
                            .orderStatus(OrderStatus.IN_PROGRESS)
                            .total(0L)
                            .subOrders(new ArrayList<>())
                            .build();
                    return ordersRepository.save(newCart);
                });
    }

    @Override
    @Transactional
    public void addToCart(Long customerId, Long productId, int quantity) {
        Orders cart = getCart(customerId);
        Product product = productRepository.findById(productId).orElseThrow();

        // Tìm hoặc tạo SubOrder cho người bán này trong giỏ hàng hiện tại
        SubOrders subOrder = cart.getSubOrders().stream()
                .filter(so -> so.getSellerOrder().getAccountId().equals(product.getSeller().getAccountId()))
                .findFirst()
                .orElseGet(() -> {
                    SubOrders newSubOrder = SubOrders.builder()
                            .order(cart)
                            .sellerOrder(product.getSeller())
                            .total(0L)
                            .orderItems(new ArrayList<>())
                            .build();
                    return subOrdersRepository.save(newSubOrder);
                });

        // Tìm xem sản phẩm đã có trong SubOrder chưa
        OrderItems item = subOrder.getOrderItems().stream()
                .filter(oi -> oi.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
            orderItemsRepository.save(item);
        } else {
            OrderItems newItem = OrderItems.builder()
                    .subOrders(subOrder)
                    .product(product)
                    .quantity(quantity)
                    .price(product.getPrice())
                    .isChosen(true)
                    .build();
            orderItemsRepository.save(newItem);
        }
        
        updateCartTotal(cart);
    }

    @Override
    @Transactional
    public void updateQuantity(Long orderItemId, int quantity) {
        OrderItems item = orderItemsRepository.findById(orderItemId).orElseThrow();
        if (quantity <= 0) {
            removeFromCart(orderItemId);
        } else {
            item.setQuantity(quantity);
            orderItemsRepository.save(item);
            updateCartTotal(item.getSubOrders().getOrder());
        }
    }

    @Override
    @Transactional
    public void removeFromCart(Long orderItemId) {
        OrderItems item = orderItemsRepository.findById(orderItemId).orElseThrow();
        Orders cart = item.getSubOrders().getOrder();
        SubOrders subOrder = item.getSubOrders();
        
        orderItemsRepository.delete(item);
        
        // Nếu SubOrder không còn item nào, xóa SubOrder
        if (subOrder.getOrderItems().size() <= 1) { // Size <= 1 vì item hiện tại chưa thực sự bị xóa khỏi list trong memory
            subOrdersRepository.delete(subOrder);
        }
        
        updateCartTotal(cart);
    }

    @Override
    @Transactional
    public void toggleSelect(Long orderItemId) {
        OrderItems item = orderItemsRepository.findById(orderItemId).orElseThrow();
        item.setChosen(!item.isChosen());
        orderItemsRepository.save(item);
        updateCartTotal(item.getSubOrders().getOrder());
    }

    private void updateCartTotal(Orders cart) {
        int total = 0;
        Orders currentCart = ordersRepository.findById(cart.getOrdersId()).orElseThrow();
        if (currentCart.getSubOrders() != null) {
            for (SubOrders so : currentCart.getSubOrders()) {
                long subTotal = 0;
                if (so.getOrderItems() != null) {
                    for (OrderItems oi : so.getOrderItems()) {
                        if (oi.isChosen()) {
                            subTotal += (long) oi.getPrice() * oi.getQuantity();
                        }
                    }
                }
                so.setTotal(subTotal);
                subOrdersRepository.save(so);
                total += (int) subTotal;
            }
        }
        currentCart.setTotal((long) total);
        ordersRepository.save(currentCart);
    }
}