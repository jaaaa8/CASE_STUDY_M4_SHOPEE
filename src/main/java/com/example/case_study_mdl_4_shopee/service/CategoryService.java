package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.Category;
import com.example.case_study_mdl_4_shopee.repository.ICategoryRepository;
import com.example.case_study_mdl_4_shopee.service.impl.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
}
