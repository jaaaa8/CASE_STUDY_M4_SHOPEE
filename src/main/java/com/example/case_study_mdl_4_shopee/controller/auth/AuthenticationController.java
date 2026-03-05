package com.example.case_study_mdl_4_shopee.controller.auth;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class AuthenticationController {
    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model) {

        Collection<?> roles = (Collection<?>) session.getAttribute("roles");

        if (roles != null && roles.size() > 1) {
            model.addAttribute("roles", roles);
        }

        return "/auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "/auth/register";
    }

}
