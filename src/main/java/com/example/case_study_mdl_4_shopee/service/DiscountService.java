package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.Discount;
import com.example.case_study_mdl_4_shopee.enums.DiscountType;
import com.example.case_study_mdl_4_shopee.repository.IDiscountRepository;
import com.example.case_study_mdl_4_shopee.service.impl.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountService implements IDiscountService {
    @Autowired
    private IDiscountRepository discountRepository;
    @Override
    public Discount validateAndCalculate(String code, Long currentOrderTotal) {
        // 1. Tìm mã trong DB
        Discount discount = discountRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Mã giảm giá không tồn tại!"));

        // 2. Kiểm tra thời hạn
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(discount.getStartDate()) || now.isAfter(discount.getEndDate())) {
            throw new RuntimeException("Mã giảm giá hiện không trong thời gian sử dụng!");
        }

        // 3. Kiểm tra lượt dùng
        if (discount.getUsedCount() >= discount.getUsageLimit()) {
            throw new RuntimeException("Mã giảm giá đã hết lượt sử dụng!");
        }

        // 4. Kiểm tra giá trị đơn hàng tối thiểu
        if (currentOrderTotal < discount.getMinOrderValue()) {
            throw new RuntimeException("Đơn hàng chưa đạt giá trị tối thiểu " + discount.getMinOrderValue() + "đ");
        }

        return discount;
    }
    @Override
    public Integer calculateDiscountAmount(Discount discount, Long orderTotal) {
        Integer amount = 0;
        if (discount.getDiscountType() == DiscountType.PERCENT) {
            // Tính theo %
            amount = (int) (orderTotal * discount.getDiscountValue() / 100);
            // Giới hạn mức giảm tối đa (nếu có)
            if (discount.getMaxDiscount() != null && amount > discount.getMaxDiscount()) {
                amount = discount.getMaxDiscount();
            }
        } else {
            // Tính theo số tiền cố định
            amount = discount.getDiscountValue();
        }
        return amount;
    }

    @Override
    public Optional<Discount> findByCode(String code) {
        return discountRepository.findByCode(code);
    }

    @Override
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    @Override
    public void save(Discount discount) {
        discountRepository.save(discount);
    }

    @Override
    public void deleteById(Integer id) {
        discountRepository.deleteById(id);
    }

}
