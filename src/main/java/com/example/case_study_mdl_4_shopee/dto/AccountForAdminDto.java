package com.example.case_study_mdl_4_shopee.dto;

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
}
