package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.entity.Product;
import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product getProductById(Long productId);
    List<Product> searchProductsByName(String name);
    List<Product> getProductsBySeller(Long sellerId);
}