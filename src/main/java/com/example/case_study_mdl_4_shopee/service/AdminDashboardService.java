package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.dto.DashboardDto;

import com.example.case_study_mdl_4_shopee.repository.IAccountRepository;
import com.example.case_study_mdl_4_shopee.repository.IOrdersRepository;
import com.example.case_study_mdl_4_shopee.repository.IProductRepository;

import com.example.case_study_mdl_4_shopee.service.impl.IAdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDashboardService implements IAdminDashboardService {

    private final IAccountRepository accountRepository;

    private final IProductRepository productRepository;

    private final IOrdersRepository orderRepository;

    @Override
    public DashboardDto getDashboardData() {

        DashboardDto dto = new DashboardDto();

        dto.setTotalCustomers(accountRepository.count());

        dto.setTotalProducts(productRepository.count());

        dto.setTotalOrders(orderRepository.count());

        dto.setRecentOrders(orderRepository.findTop5ByOrderByCreatedAtDesc());

        return dto;
    }
}