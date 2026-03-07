package com.example.case_study_mdl_4_shopee.dto;

import com.example.case_study_mdl_4_shopee.entity.Orders;
import lombok.Data;

import java.util.List;

@Data
public class DashboardDto {

    private long totalCustomers;

    private long totalProducts;

    private long totalOrders;

    private List<Orders> recentOrders;

}