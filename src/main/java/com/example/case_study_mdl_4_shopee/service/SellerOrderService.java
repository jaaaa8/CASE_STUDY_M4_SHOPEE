package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.OrderItems;
import com.example.case_study_mdl_4_shopee.entity.Product;
import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import com.example.case_study_mdl_4_shopee.enums.SubOrderStatus;
import com.example.case_study_mdl_4_shopee.repository.IProductRepository;
import com.example.case_study_mdl_4_shopee.repository.ISubOrdersRepository;
import com.example.case_study_mdl_4_shopee.service.impl.ISellerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerOrderService implements ISellerOrderService {

    @Autowired
    private ISubOrdersRepository subOrdersRepository;

    @Autowired
    private IProductRepository productRepository; // Giả sử tên Repository của Product

    @Override
    public void confirmOrder(Long subOrderId) {
        SubOrders subOrder = subOrdersRepository.findById(subOrderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng của shop"));

        // Logic: Chuyển từ PENDING sang bước tiếp theo (xác nhận)
        if (subOrder.getStatus() != SubOrderStatus.PENDING) {
            throw new IllegalStateException("Đơn hàng không ở trạng thái chờ xác nhận.");
        }

        // 1. Kiểm tra và trừ kho (Stock) của từng sản phẩm trong đơn
        for (OrderItems item : subOrder.getOrderItems()) {
            Product product = item.getProduct();
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Sản phẩm " + product.getName() + " không đủ số lượng!");
            }
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        // 2. Cập nhật trạng thái (Trong Enum của bạn, PENDING có thể coi là đã xác nhận chờ vận chuyển)
        // Hoặc bạn có thể giữ nguyên PENDING nhưng đổi OrderStatus tổng sang IN_PROGRESS
        subOrder.setStatus(SubOrderStatus.PENDING);
        subOrdersRepository.save(subOrder);
    }

    @Override
    public void rejectOrder(Long subOrderId) {
        SubOrders subOrder = subOrdersRepository.findById(subOrderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        if (subOrder.getStatus() != SubOrderStatus.PENDING) {
            throw new IllegalStateException("Chỉ có thể từ chối đơn hàng đang chờ xác nhận.");
        }

        // Cập nhật trạng thái hủy
        subOrder.setStatus(SubOrderStatus.CANCELLED);
        subOrdersRepository.save(subOrder);
    }

    @Override
    public void shipOrder(Long subOrderId) {
        SubOrders subOrder = subOrdersRepository.findById(subOrderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        // Logic: Seller đánh dấu đã giao hàng cho đơn vị vận chuyển
        // Chuyển sang SHIPPER_RECEIVED theo Enum của bạn
        subOrder.setStatus(SubOrderStatus.SHIPPER_RECEIVED);

        subOrdersRepository.save(subOrder);
    }
}
