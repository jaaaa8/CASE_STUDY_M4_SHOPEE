package com.example.case_study_mdl_4_shopee.service.impl;

public interface ISellerOrderService {
    void confirmOrder(Long orderId);
    // xác nhận đơn hàng bởi người bán, chuyển trạng thái từ "PENDING" sang "CONFIRMED"

    void rejectOrder(Long orderId);
    // từ chối đơn hàng bởi người bán, chuyển trạng thái từ "PENDING" sang "REJECTED"

    void shipOrder(Long orderId);
    // đánh dấu đơn hàng đã được giao, chuyển trạng thái từ "CONFIRMED" sang "SHIPPED"
}
