package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.entity.ShippingTask;
import com.example.case_study_mdl_4_shopee.entity.SubOrders;

import java.util.List;

public interface IShipperOrderService {
    void confirmArrivedWarehouse(Long adminShipperId, Long subOrderId);
    void confirmPickup(Long shipperId, Long subOrderId);
    void confirmDelivered(Long shipperId, Long subOrderId);
    List<ShippingTask> getShipperTasks(Long shipperId);
    List<SubOrders> getTaskDetail(Long taskId);
    void createTaskForSubOrder(SubOrders subOrder);
}
