package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByNameContaining(String name, Pageable pageable);
}
