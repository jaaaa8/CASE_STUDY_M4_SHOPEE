package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.dto.ProductForAdminDto;
import com.example.case_study_mdl_4_shopee.entity.Product;

import java.util.List;

public interface IAdminProductService {
    List<ProductForAdminDto> listProduct();
    Product findById(Long id);

    ProductForAdminDto findDtoById(Long id);
    boolean forceDeleteProduct(Long productId);
    // xoá sản phẩm
    List<ProductForAdminDto> search(String name,
                                    String shopName,
                                    Integer categoryId);
}
