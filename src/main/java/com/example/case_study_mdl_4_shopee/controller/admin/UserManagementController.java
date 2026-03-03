package com.example.case_study_mdl_4_shopee.controller.admin;

import com.example.case_study_mdl_4_shopee.dto.AccountForAdminDto;
import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.service.impl.IUserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/account")
public class UserManagementController {
    @Autowired
    private final IUserManagementService userManagementService;

    public UserManagementController(IUserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("")
    public String listUsers(Model model) {
        List<AccountForAdminDto> accounts = userManagementService.listAccounts();
        model.addAttribute("accounts", accounts);
        return "admin/account/account_list";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Account account = userManagementService.findById(id);
        if(account == null) {
            return "redirect:/admin/account";
        }
        model.addAttribute("account", account);
        return "admin/account/account_detail";
    }
}
