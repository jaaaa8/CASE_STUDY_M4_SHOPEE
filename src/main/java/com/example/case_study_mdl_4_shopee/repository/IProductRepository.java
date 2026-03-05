package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findBySeller_AccountId(Long sellerId);
    @Query(value = """
        SELECT p.* FROM product p
        LEFT JOIN account a ON p.seller_id = a.account_id
        WHERE (:name IS NULL OR p.name LIKE %:name%)
        AND (:shopName IS NULL OR a.username LIKE %:shopName%)
        AND (:categoryId IS NULL OR p.category_id = :categoryId)
        """, nativeQuery = true)
    List<Product> search(
            @Param("name") String name,
            @Param("shopName") String shopName,
            @Param("categoryId") Integer categoryId
    );
}
