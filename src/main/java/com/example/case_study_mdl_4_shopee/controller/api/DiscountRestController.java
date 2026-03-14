package com.example.case_study_mdl_4_shopee.controller.api;

import com.example.case_study_mdl_4_shopee.entity.Discount;
import com.example.case_study_mdl_4_shopee.enums.DiscountType;
import com.example.case_study_mdl_4_shopee.service.impl.IDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/discount")
@RequiredArgsConstructor
public class DiscountRestController {

    private final IDiscountService discountService;

    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkVoucher(
            @RequestParam String code,
            @RequestParam Long orderTotal) {

        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Discount> discount = discountService.findByCode(code);

            if (discount.isEmpty()) {
                response.put("success", false);
                response.put("message", "Mã không tồn tại!");
                return ResponseEntity.ok(response);
            }

            // Kiểm tra thời hạn và số lượng (usageLimit) nếu cần...

            // TÍNH TOÁN GIÁ TRỊ GIẢM THỰC TẾ
            long calculatedDiscount = 0;

            // Dùng Enum DiscountType của bạn
            if (discount.get().getDiscountType() == DiscountType.PERCENT) {
                // Tính theo %: (Giá trị đơn hàng * phần trăm giảm) / 100
                calculatedDiscount = (orderTotal * discount.get().getDiscountValue()) / 100;

                // KIỂM TRẢ GIẢM TỐI ĐA (maxDiscount) nếu có đặt
                if (discount.get().getMaxDiscount() != null && calculatedDiscount > discount.get().getMaxDiscount()) {
                    calculatedDiscount = discount.get().getMaxDiscount();
                }
            } else {
                // Trường hợp DiscountType.AMOUNT hoặc FIXED
                calculatedDiscount = discount.get().getDiscountValue();
            }

            // Đảm bảo số tiền giảm không vượt quá tổng tiền đơn hàng
            if (calculatedDiscount > orderTotal) {
                calculatedDiscount = orderTotal;
            }

            response.put("success", true);
            response.put("discountValue", calculatedDiscount); // Trả về con số tiền đã quy đổi
            response.put("message", "Áp dụng thành công!");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống!");
        }
        return ResponseEntity.ok(response);
    }
}