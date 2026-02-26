package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.dto.AccountForAdminDto;
import com.example.case_study_mdl_4_shopee.entity.Account;

import java.util.List;

public interface IUserManagementService {
    List<AccountForAdminDto> listAccounts();
    Account findByUsername(String username);
    Account findById(int id);
    void deleteUserAccount(int userId);
    void addAdminAccount(Account account);
    void lockUserAccount(int userId);
    // khóa tài khoản người dùng

    void unlockUserAccount(int userId);
    // mở khóa tài khoản người dùng

    void grantCertificatedSeller(int userId);
    // cấp quyền người bán uy tín cho tài khoản người dùng
}
