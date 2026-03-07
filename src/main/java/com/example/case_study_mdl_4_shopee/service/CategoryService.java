package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.Category;
import com.example.case_study_mdl_4_shopee.repository.ICategoryRepository;
import com.example.case_study_mdl_4_shopee.service.impl.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
    @Override
    public Page<Category> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> search(String name, Pageable pageable) {
        return categoryRepository.findByNameContaining(name, pageable);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
