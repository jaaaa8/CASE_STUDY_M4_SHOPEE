package com.example.case_study_mdl_4_shopee.controller.admin;

import com.example.case_study_mdl_4_shopee.dto.OrderAdminDto;
import com.example.case_study_mdl_4_shopee.enums.OrderStatus;
import com.example.case_study_mdl_4_shopee.service.impl.IAdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/order")
public class OrderManagementController {
    @Autowired
    private IAdminOrderService adminOrderService;

//    @GetMapping("")
//    public String showList(Model model){
//
//        model.addAttribute(
//                "orders",
//                adminOrderService.getAllOrders()
//        );
//
//        return "admin/order/order_list";
//    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model){

        model.addAttribute(
                "order",
                adminOrderService.getOrderDetail(id)
        );

        return "admin/order/order_detail";
    }
    @GetMapping("")
    public String listOrders(
            @RequestParam(required = false) String orderCode,
            @RequestParam(required = false) String customer,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {

        Page<OrderAdminDto> orders =
                adminOrderService.searchOrders(
                        orderCode,
                        customer,
                        status,
                        PageRequest.of(page, 10)
                );

        model.addAttribute("orders", orders);
        model.addAttribute("statuses", OrderStatus.values());


        return "admin/order/order_list";
    }
    @PostMapping("/update-status/{id}")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ) {

        adminOrderService.updateStatus(id, status);

        return "redirect:/admin/order";
    }
}
