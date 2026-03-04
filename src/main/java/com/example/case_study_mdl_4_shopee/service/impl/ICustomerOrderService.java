package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.entity.Orders;

import java.util.List;

public interface ICustomerOrderService {
    Orders checkout(Long customerId, String paymentMethod, Long productId, Integer quantity);
    void cancelOrder(Long orderId);
    List<Orders> viewOrderHistory(Long customerId);
}
