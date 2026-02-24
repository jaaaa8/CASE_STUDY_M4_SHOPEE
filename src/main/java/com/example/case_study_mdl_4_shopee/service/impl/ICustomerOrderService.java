package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.entity.Orders;

import java.util.List;

public interface ICustomerOrderService {
    void checkout();
    // sẽ tạo một đơn hàng mới dựa trên giỏ hàng hiện tại của customer (check bằng is_chosen trong order_items)
    // sau đó lưu thông tin đơn hàng vào cơ sở dữ liệu và xóa giỏ hàng.


    void cancelOrder();
    // sẽ cho phép khách hàng hủy đơn hàng nếu đơn hàng chưa được xác nhận (status = "PENDING").
    // Khi hủy đơn hàng, trạng thái của đơn hàng sẽ được cập nhật thành "CANCELED"

    List<Orders> viewOrderHistory(Long customerId);
    // sẽ hiển thị lịch sử đơn hàng của khách hàng
}
