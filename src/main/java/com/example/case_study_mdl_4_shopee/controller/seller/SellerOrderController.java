package com.example.case_study_mdl_4_shopee.controller.seller;

import com.example.case_study_mdl_4_shopee.service.impl.ISellerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/seller/orders")
public class SellerOrderController {
    private final ISellerOrderService sellerOrderService;

    // Sử dụng Constructor Injection theo tài liệu bạn đã cung cấp
    public SellerOrderController(ISellerOrderService sellerOrderService) {
        this.sellerOrderService = sellerOrderService;
    }

    @PutMapping("/{orderId}/confirm")
    public ResponseEntity<String> confirmOrder(@PathVariable Long orderId) {
        sellerOrderService.confirmOrder(orderId);
        return ResponseEntity.ok("Đơn hàng đã được xác nhận thành công!");
    }

    @PutMapping("/{orderId}/reject")
    public ResponseEntity<String> rejectOrder(@PathVariable Long orderId) {
        sellerOrderService.rejectOrder(orderId);
        return ResponseEntity.ok("Đơn hàng đã bị từ chối.");
    }

    @PutMapping("/{orderId}/ship")
    public ResponseEntity<String> shipOrder(@PathVariable Long orderId) {
        sellerOrderService.shipOrder(orderId);
        return ResponseEntity.ok("Đơn hàng đã được bàn giao cho đơn vị vận chuyển.");
    }
}
