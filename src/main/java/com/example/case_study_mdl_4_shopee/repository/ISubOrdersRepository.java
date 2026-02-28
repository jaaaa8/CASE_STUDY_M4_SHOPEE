package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ISubOrdersRepository extends JpaRepository<SubOrders, Long> {
    List<SubOrders> findBySellerId(Long sellerId);
    Optional<SubOrders> findBySubOrderIdAndSellerId(Long subOrderId, Long sellerId);

}