package com.example.case_study_mdl_4_shopee.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Authentication authentication) {

        if (authentication.getAuthorities()
                .stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), "ADMIN"))) {

            return "admin/home";
        }

        return "user/customer/home";
    }
}