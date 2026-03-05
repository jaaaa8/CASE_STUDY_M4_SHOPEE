package com.example.case_study_mdl_4_shopee.service.impl;

public interface IAuthenticationService {
    boolean register(String username, String password, String email, String phone, String address);
    boolean logout(String token);
    boolean isAuthenticated(String token);
    String refreshToken(String token);
    String generateToken(String username);
}
