package com.example.case_study_mdl_4_shopee.service.impl;

public interface IAuthenticationService {
    boolean register(String username, String password, String email, String phone, String address);
    String refreshToken(String token);
    String generateToken(String username);
}
