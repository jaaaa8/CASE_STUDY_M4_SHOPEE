package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.Orders;
import com.example.case_study_mdl_4_shopee.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IOrdersRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByCustomerOrder_AccountIdAndOrderStatus(Long customerId, OrderStatus status);
    @Query("""
        SELECT o FROM Orders o
        JOIN FETCH o.customerOrder
        ORDER BY o.createdAt DESC
    """)
    List<Orders> findAllOrders();
    @Query("""
    SELECT o FROM Orders o
    WHERE (:orderCode IS NULL OR o.orderCode LIKE %:orderCode%)
    AND (:customer IS NULL OR o.customerOrder.username LIKE %:customer%)
    AND (:status IS NULL OR o.orderStatus = :status)
""")
    Page<Orders> searchOrders(
            @Param("orderCode") String orderCode,
            @Param("customer") String customer,
            @Param("status") OrderStatus status,
            Pageable pageable
    );
    List<Orders> findTop5ByOrderByCreatedAtDesc();

    Optional<Orders> findByOrdersIdAndCustomerOrder_AccountId(Long orderId, Long customerId);
    @Query("""
    SELECT o
    FROM Orders o
    LEFT JOIN FETCH o.subOrders
    WHERE o.ordersId = :orderId
    AND o.customerOrder.accountId = :customerId
    """)
    Optional<Orders> findOrderWithSubOrders(Long orderId, Long customerId);
}
