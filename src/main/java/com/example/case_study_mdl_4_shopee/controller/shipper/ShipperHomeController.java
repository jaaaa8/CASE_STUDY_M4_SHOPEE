package com.example.case_study_mdl_4_shopee.controller.shipper;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.ShippingTask;
import com.example.case_study_mdl_4_shopee.repository.IAccountRepository;
import com.example.case_study_mdl_4_shopee.service.ShipperOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/shipment")
@RequiredArgsConstructor
public class ShipperHomeController {
    private final ShipperOrderService shipperOrderService;
    private final IAccountRepository accountRepository;

    @GetMapping("/home")
    public String shipperHome(Authentication authentication, Model model) {

        String username = authentication.getName();

        Long shipperId = accountRepository.findByUsername(username)
                .map(Account::getAccountId)
                .orElse(null);;

        List<ShippingTask> tasks = shipperOrderService.getShipperTasks(shipperId);

        model.addAttribute("tasks", tasks);

        return "/user/shipment/shipper/schedule";
    }
}
