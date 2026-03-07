package com.example.case_study_mdl_4_shopee.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderAdminDto {

    private Long ordersId;

    private String orderCode;

    private String customerName;

    private String orderStatus;

    private Long total;

    private LocalDateTime createdAt;
}
