package com.example.case_study_mdl_4_shopee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderAdminDto {

    private Long ordersId;

    private String orderCode;

    private String customerName;

    private String orderStatus;

    private Long total;

    private LocalDateTime createdAt;
    private String sellerName;



}
