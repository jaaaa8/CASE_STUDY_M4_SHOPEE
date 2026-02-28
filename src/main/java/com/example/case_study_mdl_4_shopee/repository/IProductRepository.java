package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.*;

public interface IProductRepository extends JpaRepository<Product, Long> {

    List<Product> findBySellerId(Long sellerId);


    Optional<Product> findByProductIdAndSellerId(Long productId, Long sellerId);

    // Lock để trừ stock khi confirm đơn
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.productId = :id")
    Optional<Product> findByIdForUpdate(@Param("id") Long id);
}