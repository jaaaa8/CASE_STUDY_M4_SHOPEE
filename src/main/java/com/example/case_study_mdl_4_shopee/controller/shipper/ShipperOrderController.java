package com.example.case_study_mdl_4_shopee.controller.shipper;

import com.example.case_study_mdl_4_shopee.entity.ShippingTask;
import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import com.example.case_study_mdl_4_shopee.service.ShipperOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shipper/orders")
@RequiredArgsConstructor
public class ShipperOrderController {

    private final ShipperOrderService shipperOrderService;

    /* =========================
       1. View shipper tasks
    ========================= */

    @GetMapping("/tasks/{shipperId}")
    public String getShipperTasks(@PathVariable Long shipperId, Model model) {

        List<ShippingTask> tasks = shipperOrderService.getShipperTasks(shipperId);

        model.addAttribute("tasks", tasks);

        return "user/shipment/shipper/schedule";
    }

    /* =========================
       2. Task detail
    ========================= */

    @GetMapping("/tasks/detail/{taskId}")
    public String getTaskDetail(@PathVariable Long taskId, Model model) {

        List<SubOrders> subOrders = shipperOrderService.getTaskDetail(taskId);

        model.addAttribute("subOrders", subOrders);

        return "user/shipment/shipper/task-detail";
    }

    /* =========================
       3. Confirm pickup
    ========================= */

    @PostMapping("/confirm-pickup")
    public String confirmPickup(@RequestParam Long shipperId,
                                @RequestParam Long subOrderId) {

        shipperOrderService.confirmPickup(shipperId, subOrderId);

        return "redirect:/shipper/home";
    }

    /* =========================
       4. Confirm delivered
    ========================= */

    @PostMapping("/confirm-delivered")
    public String confirmDelivered(@RequestParam Long shipperId,
                                   @RequestParam Long subOrderId) {

        shipperOrderService.confirmDelivered(shipperId, subOrderId);

        return "redirect:/shipper/home";
    }
}