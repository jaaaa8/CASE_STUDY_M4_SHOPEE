package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.repository.IAccountRepository;
import com.example.case_study_mdl_4_shopee.service.impl.IShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService implements IShopService {

    private final IAccountRepository accountRepository;

    @Override
    public List<Account> searchShopsByName(String name) {
        return accountRepository.findByUsernameContainingIgnoreCase(name);
    }

    @Override
    public Account getShopById(Long shopId) {
        return accountRepository.findById(shopId).orElse(null);
    }
}