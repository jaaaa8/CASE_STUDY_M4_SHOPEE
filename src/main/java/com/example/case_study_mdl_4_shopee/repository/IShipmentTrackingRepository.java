package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.ShipmentTracking;
import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IShipmentTrackingRepository extends JpaRepository<ShipmentTracking, Long> {
    @Query("""
        SELECT st
        FROM ShipmentTracking st
        JOIN FETCH st.subOrder so
        LEFT JOIN FETCH st.warehouse w
        LEFT JOIN FETCH st.updatedBy u
        WHERE so.order.ordersId = :orderId
        ORDER BY so.subOrderId, st.createdAt
        """)
    List<ShipmentTracking> findTrackingByOrderId(Long orderId);

    ShipmentTracking findShipmentTrackingBySubOrder(SubOrders subOrder);
}
