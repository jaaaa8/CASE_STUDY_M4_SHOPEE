package com.example.case_study_mdl_4_shopee.controller.api;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.service.impl.IAuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class AuthApiController {

    private final IAuthenticationService authenticationService;

    public AuthApiController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public String login(Account account, HttpServletResponse response) {

        String token = authenticationService.login(
                account.getUsername(),
                account.getPassword()
        );

        if (token.isEmpty()) {
            return "redirect:/login?error";
        }

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        return "redirect:/home";
    }

    @PostMapping("/register")
    public String register(Account account) {

        boolean result = authenticationService.register(
                account.getUsername(),
                account.getPassword(),
                account.getEmail(),
                account.getPhone(),
                account.getAddress()
        );

        if (!result) {
            return "redirect:/register?error";
        }

        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("jwt", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);

        return "redirect:/login";
    }
}