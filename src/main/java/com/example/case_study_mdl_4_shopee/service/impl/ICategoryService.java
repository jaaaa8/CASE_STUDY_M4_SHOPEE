package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
    Page<Category> getAll(Pageable pageable);

    Page<Category> search(String name, Pageable pageable);

    Category findById(Long id);

    void save(Category category);

    void delete(Long id);
}
