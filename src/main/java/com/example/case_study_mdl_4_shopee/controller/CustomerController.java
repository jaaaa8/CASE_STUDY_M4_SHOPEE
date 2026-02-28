package com.example.case_study_mdl_4_shopee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    @GetMapping("/")
    public String home() {
        return "user/customer/home";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @GetMapping("/product/detail")
    public String productDetail() {
        return "user/customer/product/detail";
    }

    @GetMapping("/cart")
    public String cart() {
        return "user/customer/cart/cart";
    }

    @GetMapping("/payment")
    public String payment() {
        return "user/customer/payment/payment";
    }

    @GetMapping("/profile")
    public String profile() {
        return "user/profile";
    }

    @GetMapping("/admin")
    public String adminHome() {
        return "admin/home";
    }

    @GetMapping("/seller")
    public String sellerHome() {
        return "user/seller/home";
    }

    @GetMapping("/seller/product/create")
    public String sellerProductCreate() {
        return "user/seller/order/create";
    }

    @GetMapping("/seller/product/update")
    public String sellerProductUpdate() {
        return "user/seller/order/update";
    }
}
