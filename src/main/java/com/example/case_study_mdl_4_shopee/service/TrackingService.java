package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.dto.OrderTrackingDto;
import com.example.case_study_mdl_4_shopee.dto.ShipmentTrackingDto;
import com.example.case_study_mdl_4_shopee.dto.SubOrderTrackingDto;
import com.example.case_study_mdl_4_shopee.entity.ShipmentTracking;
import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import com.example.case_study_mdl_4_shopee.enums.TrackingStatus;
import com.example.case_study_mdl_4_shopee.repository.IOrdersRepository;
import com.example.case_study_mdl_4_shopee.repository.IShipmentTrackingRepository;
import com.example.case_study_mdl_4_shopee.service.impl.ITrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackingService implements ITrackingService {

    private final IShipmentTrackingRepository shipmentTrackingRepository;
    private final IOrdersRepository ordersRepository;

    @Override
    public List<ShipmentTrackingDto> getTrackingBySubOrder(Long subOrderId) {
        return List.of();
    }

    /**
     * Lấy toàn bộ tracking của order theo customerId
     */
    @Override
    public OrderTrackingDto getTrackingByOrder(Long customerId, Long orderId) {

        // kiểm tra order có thuộc customer không
        ordersRepository
                .findByOrdersIdAndCustomerOrder_AccountId(orderId, customerId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // lấy toàn bộ tracking của order
        List<ShipmentTracking> trackingList =
                shipmentTrackingRepository.findTrackingByOrderId(orderId);

        // group theo subOrderId
        Map<Long, List<ShipmentTracking>> trackingMap =
                trackingList.stream()
                        .collect(Collectors.groupingBy(
                                st -> st.getSubOrder().getSubOrderId()
                        ));

        List<SubOrderTrackingDto> subOrderTrackingDtos = new ArrayList<>();

        for (Map.Entry<Long, List<ShipmentTracking>> entry : trackingMap.entrySet()) {

            Long subOrderId = entry.getKey();
            List<ShipmentTracking> trackings = entry.getValue();

            String sellerName = trackings.get(0)
                    .getSubOrder()
                    .getSellerOrder()
                    .getUsername();

            List<ShipmentTrackingDto> trackingDtos =
                    trackings.stream()
                            .map(this::convertToDto)
                            .toList();

            SubOrderTrackingDto subDto = SubOrderTrackingDto.builder()
                    .subOrderId(subOrderId)
                    .sellerName(sellerName)
                    .trackings(trackingDtos)
                    .build();

            subOrderTrackingDtos.add(subDto);
        }

        return OrderTrackingDto.builder()
                .orderId(orderId)
                .subOrders(subOrderTrackingDtos)
                .build();
    }

    /**
     * Convert Entity -> DTO
     */

    private ShipmentTrackingDto convertToDto(ShipmentTracking tracking) {

        ShipmentTrackingDto dto = new ShipmentTrackingDto();

        dto.setTrackingId(tracking.getTrackingId());
        dto.setStatus(tracking.getStatus());
        dto.setNote(tracking.getNote());
        dto.setCreatedAt(tracking.getCreatedAt());

        if (tracking.getSubOrder() != null) {
            dto.setSubOrderId(tracking.getSubOrder().getSubOrderId());
        }

        if (tracking.getWarehouse() != null) {
            dto.setWarehouseId(tracking.getWarehouse().getWarehouseId());
            dto.setWarehouseName(tracking.getWarehouse().getName());
        }

        if (tracking.getUpdatedBy() != null) {
            dto.setUpdatedBy(tracking.getUpdatedBy().getAccountId());
        }

        return dto;
    }

}