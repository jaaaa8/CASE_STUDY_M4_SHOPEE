package com.example.case_study_mdl_4_shopee.dto;

import com.example.case_study_mdl_4_shopee.enums.TrackingStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentTrackingDto {

    private Long trackingId;

    private Long subOrderId;

    private Long warehouseId;

    private String warehouseName;

    private TrackingStatus status;

    private String note;

    private Long updatedBy;

    private LocalDateTime createdAt;
}