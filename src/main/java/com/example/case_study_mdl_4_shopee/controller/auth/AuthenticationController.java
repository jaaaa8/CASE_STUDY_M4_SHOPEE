package com.example.case_study_mdl_4_shopee.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthenticationController {
    @GetMapping("/login")
    public String loginPage() {
        return "/auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "/auth/register";
    }

}
