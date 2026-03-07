package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.dto.OrderAdminDto;
import com.example.case_study_mdl_4_shopee.dto.OrderDetailDto;
import com.example.case_study_mdl_4_shopee.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IAdminOrderService {

    Page<OrderAdminDto> searchOrders(
            String orderCode,
            String customer,
            OrderStatus status,
            Pageable pageable);

    void updateStatus(Long id, OrderStatus status);

    void refundOrder(int orderId);
    // xác nhận hoàn tiền cho khách hàng

    void forceCancelOrder(int orderId);
    // hủy đơn hàng, trả hàng về kho, hoàn tiền nếu đã thanh toán
    List<OrderAdminDto> getAllOrders();

    OrderDetailDto getOrderDetail(Long id);

}
