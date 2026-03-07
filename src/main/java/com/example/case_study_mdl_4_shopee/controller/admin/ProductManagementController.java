package com.example.case_study_mdl_4_shopee.controller.admin;

import com.example.case_study_mdl_4_shopee.dto.ProductForAdminDto;
import com.example.case_study_mdl_4_shopee.service.impl.IAdminProductService;
import com.example.case_study_mdl_4_shopee.service.impl.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class ProductManagementController {
    @Autowired
    private IAdminProductService adminProductService;
    @Autowired
    private ICategoryService categoryService;
    @GetMapping("")
    public String showList(Model model) {

        List<ProductForAdminDto> products =
                adminProductService.listProduct();

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll());


        return "admin/product/product_list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        ProductForAdminDto product = adminProductService.findDtoById(id);
        model.addAttribute("product", product);
        return "admin/product/product_detail";
    }
    @GetMapping("/search")
    public String searchProduct(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String shopName,
            @RequestParam(required = false) Integer categoryId,
            Model model
    ) {

        List<ProductForAdminDto> products =
                adminProductService.search(name, shopName, categoryId);

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll());

        return "admin/product/product_list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        ProductForAdminDto product = adminProductService.findDtoById(id);
        if (product == null) {
            return "redirect:/admin/product";
        }

        model.addAttribute("product", product);
        return "admin/product/product_list";
    }

}
