package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.entity.Orders;

public interface ICartService {
    Orders getCart(Long customerId);
    void addToCart(Long customerId, Long productId, int quantity);
    void updateQuantity(Long orderItemId, int quantity);
    void removeFromCart(Long orderItemId);
    void toggleSelect(Long orderItemId);
}