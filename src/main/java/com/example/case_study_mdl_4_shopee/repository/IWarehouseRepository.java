package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IWarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findByName(String daNangWarehouse);
}
