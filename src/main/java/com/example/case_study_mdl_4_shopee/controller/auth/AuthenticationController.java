package com.example.case_study_mdl_4_shopee.controller.auth;

import com.example.case_study_mdl_4_shopee.entity.City;
import com.example.case_study_mdl_4_shopee.repository.ICityRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
public class AuthenticationController {
    @Autowired
    private final ICityRepository cityRepository;

    public AuthenticationController(ICityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model) {

        Collection<?> roles = (Collection<?>) session.getAttribute("roles");

        if (roles != null && roles.size() > 1) {
            model.addAttribute("roles", roles);
        }

        return "/auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        List<City> cities = cityRepository.findAll();

        model.addAttribute("cities", cities);
        return "/auth/register";
    }

}
