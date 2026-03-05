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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/account")
public class UserManagementController {
    @Autowired
    private final IUserManagementService userManagementService;

    public UserManagementController(IUserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }
    // hiển thị bảng user
    @GetMapping("")
    public String listUsers(Model model) {
        List<AccountForAdminDto> accounts = userManagementService.listAccounts();
        model.addAttribute("accounts", accounts);
        return "admin/account/account_list";
    }
    //xem chi tiết user
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Account account = userManagementService.findById(id);
        if(account == null) {
            return "redirect:/admin/account";
        }
        model.addAttribute("account", account);
        return "admin/account/account_detail";
    }
    // khóa người dùng
    @GetMapping("/lock/{id}")
    public String lock(@PathVariable Long id) {
        userManagementService.lockUserAccount(id);
        return "redirect:/admin/account";
    }
    // mở khóa
    @GetMapping("/unlock/{id}")
    public String unlock(@PathVariable Long id) {
        userManagementService.unlockUserAccount(id);
        return "redirect:/admin/account";
    }
    //tìm kiếm
    @GetMapping("/search")
    public String searchAccount(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            Model model) {

        List<Account> accounts = userManagementService.search(username, email, phone);

        model.addAttribute("accounts", accounts);
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("phone", phone);

        return "admin/account/account_list";
    }
    //grantCertificatedSeller
    @GetMapping("/grantCertificatedSeller/{id}")
    public String grantCertificatedSeller(@PathVariable Long id) {
        userManagementService.grantCertificatedSeller(id);
        return "redirect:/admin/account";
    }
    @GetMapping("/removeCertificatedSeller/{id}")
    public String removeCertificatedSeller(@PathVariable Long id) {
        userManagementService.removeCertificatedSeller(id);
        return "redirect:/admin/account";
    }

}
