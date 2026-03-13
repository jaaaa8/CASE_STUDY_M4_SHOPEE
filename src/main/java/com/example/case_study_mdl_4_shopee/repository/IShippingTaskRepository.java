package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.ShippingTask;
import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import com.example.case_study_mdl_4_shopee.entity.Warehouse;
import com.example.case_study_mdl_4_shopee.enums.TaskType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IShippingTaskRepository extends JpaRepository<ShippingTask, Long> {
    List<ShippingTask> findByShipper_AccountId(Long shipperId);

    @Query("""
    SELECT t
    FROM ShippingTask t
    JOIN t.shipper a
    JOIN a.accountRoles ar
    JOIN ar.role r
    WHERE a = :shipper
    AND r.roleName = 'ROLE_SHIPPER'
    AND ar.active = true
    """)
    List<ShippingTask> findByShipper(@Param("shipper") Account shipper);

    List<ShippingTask> findByWarehouse_WarehouseId(Long warehouseId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    SELECT t
    FROM ShippingTask t
    WHERE t.warehouse = :warehouse
    AND t.type = :type
    AND t.status = 'CREATED'
    """)
    List<ShippingTask> findAvailableTasksForUpdate(Warehouse warehouse, TaskType type);

    @Query("""
    SELECT COUNT(s) > 0
    FROM SubOrders s
    JOIN s.shipper a
    WHERE a.accountId = :shipperId
    AND s.status NOT IN (
        com.example.case_study_mdl_4_shopee.enums.SubOrderStatus.DELIVERED,
        com.example.case_study_mdl_4_shopee.enums.SubOrderStatus.CANCELLED
    )
    """)
    boolean existsActiveTaskByShipperId(@Param("shipperId") Long shipperId);
}
