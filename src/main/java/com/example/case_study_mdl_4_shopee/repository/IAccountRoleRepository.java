package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.AccountRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IAccountRoleRepository extends JpaRepository<AccountRole,Long> {


    List<AccountRole> findAllByAccount_AccountId(Long accountId);
    @Modifying
    @Transactional
    @Query("UPDATE AccountRole ar SET ar.active = :status WHERE ar.account.accountId = :accId")
    void updateStatusByAccountId(@Param("accId") Long accId, @Param("status") boolean status);

    @Query("""
    SELECT ar FROM AccountRole ar
    WHERE ar.account = :account
    AND ar.role.roleName = 'SHIPPER'
    AND ar.active = true
    """)
    Optional<AccountRole> findActiveShipperRole(Account account);
}
