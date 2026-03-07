package com.example.case_study_mdl_4_shopee.dto;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountForAdminDto {
    private Long accountId;
    private String username;
    private String email;
    private String phone;
    private String address;
    private boolean active;
    private boolean certified;

    public AccountForAdminDto(Account account){
        this.accountId = account.getAccountId();
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.phone = account.getPhone();
        this.active = account.getAccountRoles().stream()
                .anyMatch(AccountRole::isActive);
    }

}
