package com.example.case_study_mdl_4_shopee.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    private String productName;

    private int quantity;

    private Long price;

}