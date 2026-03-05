package com.example.case_study_mdl_4_shopee.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerHomeController {

    @GetMapping("/home")
    public String customerHome() {
        return "user/customer/home";
    }

}