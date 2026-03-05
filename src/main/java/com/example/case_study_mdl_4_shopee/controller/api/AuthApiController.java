package com.example.case_study_mdl_4_shopee.controller.api;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.service.impl.IAuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class AuthApiController {

    private final IAuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    public AuthApiController(IAuthenticationService authenticationService, AuthenticationManager authenticationManager) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public String login(Account account, HttpServletResponse response) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            account.getUsername(),
                            account.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = authenticationService.generateToken(authentication.getName());

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            response.addCookie(cookie);

            return "redirect:/home";

        } catch (Exception e) {
            return "redirect:/login?error";
        }
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

        Cookie cookie = new Cookie("jwt", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);

        SecurityContextHolder.clearContext();

        return "redirect:/login";
    }
}