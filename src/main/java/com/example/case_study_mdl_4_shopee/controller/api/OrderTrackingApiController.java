package com.example.case_study_mdl_4_shopee.controller.api;

import com.example.case_study_mdl_4_shopee.dto.OrderTrackingDto;
import com.example.case_study_mdl_4_shopee.service.CustomerShipmentTrackingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/order")
public class OrderTrackingApiController {
    private final CustomerShipmentTrackingService trackingService;

    public OrderTrackingApiController(CustomerShipmentTrackingService trackingService) {
        this.trackingService = trackingService;
    }

    /**
     * API trả JSON
     */
    @GetMapping("{orderId}")
    @ResponseBody
    public OrderTrackingDto getTrackingByOrder(
            @PathVariable Long orderId,
            @RequestParam Long customerId
    ) {
        return trackingService.getTrackingByOrder(customerId, orderId);
    }
}
