package com.example.case_study_mdl_4_shopee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@IdClass(AccountRoleId.class)
@AllArgsConstructor
@NoArgsConstructor
public class AccountRole {
    @Id
    @ManyToOne
    @JoinColumn(name = "accountId")
    @JsonIgnore
    private Account account;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
    private Role role;

    @Column(nullable = false)
    private boolean isActive = true;
}
