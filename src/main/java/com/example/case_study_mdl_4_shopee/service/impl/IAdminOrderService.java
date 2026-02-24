package com.example.case_study_mdl_4_shopee.service.impl;

public interface IAdminOrderService {
    void refundOrder(int orderId);
    // xác nhận hoàn tiền cho khách hàng

    void forceCancelOrder(int orderId);
    // hủy đơn hàng, trả hàng về kho, hoàn tiền nếu đã thanh toán


}
