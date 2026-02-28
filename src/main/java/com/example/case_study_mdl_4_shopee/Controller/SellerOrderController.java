package com.example.case_study_mdl_4_shopee.Controller;

import com.example.case_study_mdl_4_shopee.enums.SubOrderStatus;
import com.example.case_study_mdl_4_shopee.service.SellerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/seller/orders")
@RequiredArgsConstructor
public class SellerOrderController {

    private final SellerOrderService sellerOrderService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("subOrders", sellerOrderService.getMySubOrders());
        return "user/seller/order/list";
    }

    @GetMapping("/{subOrderId}")
    public String detail(@PathVariable Long subOrderId, Model model) {
        model.addAttribute("detail", sellerOrderService.getSubOrderDetail(subOrderId));
        model.addAttribute("allStatus", SubOrderStatus.values());
        return "user/seller/order/detail";
    }

    @PostMapping("/{subOrderId}/confirm")
    public String confirm(@PathVariable Long subOrderId) {
        sellerOrderService.confirmSubOrder(subOrderId);
        return "redirect:/user/seller/orders";
    }

    @PostMapping("/{subOrderId}/status")
    public String updateStatus(@PathVariable Long subOrderId,
                               @RequestParam String status) {
        sellerOrderService.updateSubOrderStatus(subOrderId, status);
        return "redirect:/user/seller/orders/" + subOrderId;
    }

    @PostMapping("/{subOrderId}/cancel")
    public String cancel(@PathVariable Long subOrderId) {
        sellerOrderService.cancelSubOrder(subOrderId);
        return "redirect:/user/seller/orders";
    }
}
