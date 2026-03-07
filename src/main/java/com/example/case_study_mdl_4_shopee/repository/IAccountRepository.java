package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.dto.AccountForAdminDto;
import com.example.case_study_mdl_4_shopee.entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    @EntityGraph(attributePaths = {"accountRoles", "accountRoles.role"})
    Optional<Account> findByUsername(String username);
    List<Account> findByUsernameContainingIgnoreCase(String username);
    Account findByUsernameAndPassword(String username, String password);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    @Query(value = """
    SELECT * FROM account
    WHERE (:username IS NULL OR username LIKE CONCAT('%', :username, '%'))
    AND (:email IS NULL OR email LIKE CONCAT('%', :email, '%'))
    AND (:phone IS NULL OR phone LIKE CONCAT('%', :phone, '%'))
    """, nativeQuery = true)
    List<Account> searchMulti(
            @Param("username") String username,
            @Param("email") String email,
            @Param("phone") String phone
    );
}
