package com.example.case_study_mdl_4_shopee.controller.shipper;


import com.example.case_study_mdl_4_shopee.entity.ShippingTask;
import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import com.example.case_study_mdl_4_shopee.service.ShipperOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/shipper/orders")
@RequiredArgsConstructor
public class ShipperOrderController {

    private final ShipperOrderService shipperOrderService;


    /* =========================
       1. Lấy toàn bộ task của shipper
    ========================= */

    @GetMapping("/tasks/{shipperId}")
    public List<ShippingTask> getShipperTasks(@PathVariable Long shipperId) {

        return shipperOrderService.getShipperTasks(shipperId);
    }


    /* =========================
       2. Xem chi tiết task
    ========================= */

    @GetMapping("/tasks/detail/{taskId}")
    public List<SubOrders> getTaskDetail(@PathVariable Long taskId) {

        return shipperOrderService.getTaskDetail(taskId);
    }


    /* =========================
       3. Confirm pickup
    ========================= */

    @PostMapping("/confirm-pickup")
    public String confirmPickup(@RequestParam Long shipperId,
                                @RequestParam Long subOrderId) {

        shipperOrderService.confirmPickup(shipperId, subOrderId);

        return "Pickup confirmed";
    }


    /* =========================
       4. Confirm delivered
    ========================= */

    @PostMapping("/confirm-delivered")
    public String confirmDelivered(@RequestParam Long shipperId,
                                   @RequestParam Long subOrderId) {

        shipperOrderService.confirmDelivered(shipperId, subOrderId);

        return "Order delivered successfully";
    }
}
