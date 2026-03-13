package com.example.case_study_mdl_4_shopee.controller.seller;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import com.example.case_study_mdl_4_shopee.repository.IAccountRepository;
import com.example.case_study_mdl_4_shopee.repository.IOrdersRepository;
import com.example.case_study_mdl_4_shopee.repository.ISubOrdersRepository;
import com.example.case_study_mdl_4_shopee.service.impl.ISellerOrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/seller")
public class SellerHomeController {
    private final IAccountRepository accountRepository;
    private final ISellerOrderService sellerOrderService;

    public SellerHomeController(IAccountRepository accountRepository, ISellerOrderService sellerOrderService, IOrdersRepository ordersRepository, ISubOrdersRepository subOrdersRepository) {
        this.accountRepository = accountRepository;
        this.sellerOrderService = sellerOrderService;
    }

    @GetMapping("/home")
    public String sellerHome(Authentication authentication, Model model) {
        String username = authentication.getName();
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<SubOrders> subOrders = sellerOrderService.findAll(account);
        model.addAttribute("subOrders", subOrders);
        return "user/seller/home";
    }

    @GetMapping("/orders/confirm/{id}")
    public String confirmedOrders(@PathVariable String id) {
        Long orderId = Long.parseLong(id);
        sellerOrderService.confirmOrder(orderId);
        return "redirect:/seller/home";
    }
}
