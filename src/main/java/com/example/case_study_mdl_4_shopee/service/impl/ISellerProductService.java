package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.entity.Product;
import java.util.List;

public interface ISellerProductService {
    List<Product> getMyProducts(Long sellerId);
    Product create(Product product, Long sellerId);
    Product getById(Long productId, Long sellerId);
    Product update(Long productId, Product product, Long sellerId);
    void delete(Long productId, Long sellerId);
}