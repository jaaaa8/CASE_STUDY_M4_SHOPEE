package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.Orders;
import com.example.case_study_mdl_4_shopee.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrdersRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByCustomerOrder_AccountIdAndOrderStatus(Long customerId, OrderStatus status);
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
