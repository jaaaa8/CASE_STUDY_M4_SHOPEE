package com.example.case_study_mdl_4_shopee.controller.admin;

import com.example.case_study_mdl_4_shopee.dto.DashboardDto;
import com.example.case_study_mdl_4_shopee.service.impl.IAdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/home")
public class AdminHomeController {
    @Autowired
    private IAdminDashboardService dashboardService;

    @GetMapping("")
    public String dashboard(Model model){

        DashboardDto dashboardData = dashboardService.getDashboardData();

        model.addAttribute("stats", dashboardData);

        return "admin/home";
    }

}
