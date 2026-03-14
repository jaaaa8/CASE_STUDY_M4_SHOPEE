package com.example.case_study_mdl_4_shopee.controller.admin;

import com.example.case_study_mdl_4_shopee.entity.Discount;
import com.example.case_study_mdl_4_shopee.enums.DiscountType;
import com.example.case_study_mdl_4_shopee.service.impl.IDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/discounts")
@RequiredArgsConstructor
public class AdminDiscountController {

    private final IDiscountService discountService;

    // 1. Danh sách mã giảm giá
    @GetMapping
    public String listDiscounts(Model model) {
        model.addAttribute("discounts", discountService.findAll());
        return "admin/discount/list";
    }

    // 2. Trang tạo mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("discount", new Discount());
        model.addAttribute("types", DiscountType.values()); // Gửi Enum PERCENT/AMOUNT ra Select box
        return "admin/discount/create";
    }

    // 3. Lưu mã giảm giá (Dùng cho cả Create và Update)
    @PostMapping("/save")
    public String saveDiscount(@ModelAttribute Discount discount, RedirectAttributes ra) {
        try {
            discountService.save(discount);
            ra.addFlashAttribute("message", "Lưu mã giảm giá thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Lỗi: Mã code đã tồn tại hoặc dữ liệu không hợp lệ!");
        }
        return "redirect:/admin/discounts";
    }

    // 4. Xóa mã
    @GetMapping("/delete/{id}")
    public String deleteDiscount(@PathVariable Integer id) {
        discountService.deleteById(id);
        return "redirect:/admin/discounts";
    }
}
