package com.example.case_study_mdl_4_shopee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountForCustomerDto {
    private Long accountId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;

}
