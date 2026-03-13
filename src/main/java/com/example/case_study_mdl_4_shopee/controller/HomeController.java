package com.example.case_study_mdl_4_shopee.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;


@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Authentication authentication, HttpSession session) {

        String role = (String) session.getAttribute("selectedRole");

        if (role == null) {
            role = authentication.getAuthorities()
                    .stream()
                    .map(a -> a.getAuthority())
                    .findFirst()
                    .orElse("ROLE_CUSTOMER");
        }

        switch (role) {

            case "ROLE_ADMIN":
                return "redirect:/admin/home";

            case "ROLE_CUSTOMER":
                return "redirect:/customer/home";

            case "ROLE_SHIPPER":
                return "redirect:/shipment/home";

            case "ROLE_SELLER":
                return "redirect:/seller/home";
        }

        return "redirect:/";
    }
}