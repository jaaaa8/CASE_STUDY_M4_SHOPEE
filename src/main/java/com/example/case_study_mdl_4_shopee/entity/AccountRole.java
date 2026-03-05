package com.example.case_study_mdl_4_shopee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRole {

    @EmbeddedId
    private AccountRoleId id;

    @ManyToOne
    @MapsId("accountId")
    // Đổi "accountId" thành "account_id" để khớp với Database
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("roleId")
    // Tương tự, đổi "roleId" thành "role_id"
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(nullable = false)
    private boolean active = true;
}
