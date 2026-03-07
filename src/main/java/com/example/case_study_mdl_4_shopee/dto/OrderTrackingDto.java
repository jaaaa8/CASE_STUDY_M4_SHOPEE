package com.example.case_study_mdl_4_shopee.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderTrackingDto {

    private Long orderId;

    private List<SubOrderTrackingDto> subOrders;

}