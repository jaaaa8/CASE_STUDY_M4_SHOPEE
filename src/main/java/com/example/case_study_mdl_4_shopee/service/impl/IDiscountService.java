package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.entity.Discount;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface IDiscountService {
    Discount validateAndCalculate(String code, Long currentOrderTotal);
    Integer calculateDiscountAmount(Discount discount, Long orderTotal);
    Optional<Discount> findByCode(String code);

    List<Discount> findAll();

    void save(Discount discount);

    void deleteById(Integer id);
}
