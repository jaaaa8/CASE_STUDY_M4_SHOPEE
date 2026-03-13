package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.dto.AccountForAdminDto;
import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.City;
import com.example.case_study_mdl_4_shopee.entity.Location;
import com.example.case_study_mdl_4_shopee.entity.Warehouse;
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

    @Query("""
    SELECT a
    FROM Account a
    JOIN a.warehouseStaff ws
    WHERE ws.warehouse.warehouseId = :warehouseId
    """)
    List<Account> findShipperByWarehouse(Long warehouseId);

    @Query("""
    SELECT a
    FROM Account a
    JOIN a.accountRoles ar
    JOIN ar.role r
    JOIN a.warehouseStaff ws
    JOIN ws.warehouse w
    WHERE r.roleName = 'ROLE_SHIPPER'
    AND ar.active = true
    AND ws.isActive = true
    AND ws.position = com.example.case_study_mdl_4_shopee.enums.StaffPosition.SHIPPER
    AND w.location = :location
    """)
    Optional<Account> findShipperByLocation(@Param("location") Location location);

}
