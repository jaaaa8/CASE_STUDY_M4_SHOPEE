package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDiscountRepository extends JpaRepository<Discount, Integer> {
    Optional<Discount> findByCode(String code);

    @Modifying
    @Query("UPDATE Discount d SET d.usedCount = d.usedCount + 1 " +
            "WHERE d.discountId = :id AND d.usedCount < d.usageLimit")
    int incrementUsedCount(@Param("id") Integer id);
}
