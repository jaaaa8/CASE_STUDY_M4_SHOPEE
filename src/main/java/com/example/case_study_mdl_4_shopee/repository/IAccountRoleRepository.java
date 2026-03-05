package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAccountRoleRepository extends JpaRepository<AccountRole,Long> {
    Optional<AccountRole> findAllByAccount_AccountId(Long accountId);

    List<AccountRole> findByAccount_AccountId(Long accountId);
}
