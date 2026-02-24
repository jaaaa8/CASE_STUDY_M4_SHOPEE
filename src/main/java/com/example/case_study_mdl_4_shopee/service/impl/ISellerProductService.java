package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.entity.Product;

public interface ISellerProductService {
    Product createProduct(Product product);
    // tạo mới sản phẩm

    void updateProduct(Product product);
    // cập nhật thông tin sản phẩm

    void deleteProduct(Long productId);
    // xóa sản phẩm
}
