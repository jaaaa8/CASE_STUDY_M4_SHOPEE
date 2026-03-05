package com.example.case_study_mdl_4_shopee.controller.auth;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChooseRoleController {

    @GetMapping("/choose-role")
    public String chooseRole(HttpSession session, Model model) {

        Object roles = session.getAttribute("roles");

        if (roles == null) {
            return "redirect:/home";
        }

        model.addAttribute("roles", roles);

        return "auth/choose_role";
    }
}