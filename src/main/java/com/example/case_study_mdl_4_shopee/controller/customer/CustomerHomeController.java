package com.example.case_study_mdl_4_shopee.controller.customer;
import com.example.case_study_mdl_4_shopee.service.impl.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerHomeController {

    private final IProductService productService;

    @GetMapping("/home")
    public String customerHome(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "user/customer/home";
    }

}