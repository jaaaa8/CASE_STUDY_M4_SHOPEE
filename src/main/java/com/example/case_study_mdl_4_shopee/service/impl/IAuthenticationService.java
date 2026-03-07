package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.City;

public interface IAuthenticationService {
    boolean register(String username, String password, String email, String phone, String address, City city);
    String refreshToken(String token);
    String generateToken(String username);

    Account getCurrentAccount();
}
