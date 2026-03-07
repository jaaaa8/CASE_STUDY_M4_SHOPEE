package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.ShippingTask;
import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IShippingTaskRepository extends JpaRepository<ShippingTask, Long> {
    List<ShippingTask> findByShipper_AccountId(Long shipperId);

    List<ShippingTask> findByShipper(Account shipper);

}
