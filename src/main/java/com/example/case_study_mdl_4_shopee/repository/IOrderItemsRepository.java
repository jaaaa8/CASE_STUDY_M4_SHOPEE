package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface IOrderItemsRepository extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findBySubOrderId(Long subOrderId);

}