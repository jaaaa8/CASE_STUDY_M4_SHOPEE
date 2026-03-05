package com.example.case_study_mdl_4_shopee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "account_role",
        indexes = {
                @Index(name = "idx_account_role_account", columnList = "account_id"),
                @Index(name = "idx_account_role_role", columnList = "role_id"),
                @Index(name = "idx_account_role_active", columnList = "active")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRole {

    @EmbeddedId
    private AccountRoleId id = new AccountRoleId();

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(nullable = false)
    private boolean active = true;
}