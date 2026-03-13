package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.dto.OrderTrackingDto;
import com.example.case_study_mdl_4_shopee.dto.ShipmentTrackingDto;

import java.util.List;

public interface ITrackingService {
    List<ShipmentTrackingDto> getTrackingBySubOrder(Long subOrderId);
    OrderTrackingDto getTrackingByOrder(Long customerId, Long orderId);
}
