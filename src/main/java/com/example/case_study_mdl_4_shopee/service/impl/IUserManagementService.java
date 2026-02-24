package com.example.case_study_mdl_4_shopee.service.impl;

public interface IUserManagementService {
    void lockUserAccount(int userId);
    // khóa tài khoản người dùng

    void unlockUserAccount(int userId);
    // mở khóa tài khoản người dùng

    void grantCertificatedSeller(int userId);
    // cấp quyền người bán uy tín cho tài khoản người dùng
}
