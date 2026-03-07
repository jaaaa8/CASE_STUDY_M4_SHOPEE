package com.example.case_study_mdl_4_shopee.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDetailDto {

    private Long ordersId;

    private String orderCode;

    private String customerName;

    private String orderStatus;

    private Long total;

    private LocalDateTime createdAt;

    private List<OrderItemDto> items;

}
