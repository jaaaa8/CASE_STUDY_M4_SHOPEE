package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.dto.AccountForAdminDto;
import com.example.case_study_mdl_4_shopee.entity.Account;

import java.util.List;

public interface IUserManagementService {
    List<AccountForAdminDto> listAccounts();
    Account findByUsername(String username);
    Account findById(Long id);
    void lockUserAccount(Long userId);
    public List<Account> search(String username, String email, String phone);
    // khóa tài khoản người dùng

    void unlockUserAccount(Long userId);
    // mở khóa tài khoản người dùng

    void grantCertificatedSeller(Long userId);
    // cấp quyền người bán uy tín cho tài khoản người dùng
    void deleteUserAccount(Long userId);
    void addAdminAccount(Account account);
    void removeCertificatedSeller(Long id);
    void deposit(Long userId, Long amount);

    Account getCurrentAccount();
}
