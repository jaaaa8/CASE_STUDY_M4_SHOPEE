package com.example.case_study_mdl_4_shopee.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubOrderTrackingDto {

    private Long subOrderId;

    private String sellerName;
    private List<ShipmentTrackingDto> trackings;

}