package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.entity.Account;
import java.util.List;

public interface IShopService {
    List<Account> searchShopsByName(String name);
    Account getShopById(Long shopId);
}