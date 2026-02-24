package com.example.case_study_mdl_4_shopee.service.impl;

public interface IAdminProductService {
    void forceDeleteProduct(Long productId);
    // xoá sản phẩm

    void grantCertificatedProduct(Long productId);
    // cấp chứng nhận sản phẩm
}
