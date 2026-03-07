package com.example.case_study_mdl_4_shopee.controller.customer;

import com.example.case_study_mdl_4_shopee.dto.OrderTrackingDto;
import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.service.TrackingService;
import com.example.case_study_mdl_4_shopee.service.impl.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer/orders")
@RequiredArgsConstructor
public class CustomerTrackingOrderController {

    private final TrackingService trackingService;
    private final IAuthenticationService authenticationService;

    @GetMapping("/{orderId}/tracking")
    public String tracking(@PathVariable Long orderId, Model model) {

        Account currentAccount = authenticationService.getCurrentAccount();

        OrderTrackingDto tracking = trackingService.getTrackingByOrder(currentAccount.getAccountId(), orderId);

        model.addAttribute("orderTracking", tracking);

        return "user/customer/shipment_tracking/tracking";
    }
}
