package com.example.case_study_mdl_4_shopee.Controller;

import com.example.case_study_mdl_4_shopee.entity.Product;
import com.example.case_study_mdl_4_shopee.service.SellerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/seller/products")
@RequiredArgsConstructor
public class SellerProductController {

    private final SellerProductService sellerProductService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", sellerProductService.getMyProducts());
        return "user/seller/product/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        return "user/seller/product/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Product product) {
        sellerProductService.createProduct(product);
        return "redirect:/user/seller/products";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", sellerProductService.getMyProductById(id));
        return "user/seller/product/update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Product product) {
        sellerProductService.updateProduct(id, product);
        return "redirect:/user/seller/products";
    }
}