package com.example.case_study_mdl_4_shopee.controller.admin;

import com.example.case_study_mdl_4_shopee.entity.Category;
import com.example.case_study_mdl_4_shopee.service.CategoryService;
import com.example.case_study_mdl_4_shopee.service.impl.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    private final ICategoryService categoryService;

    @GetMapping
    public String listCategory(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Page<Category> categories;

        if (name != null && !name.isEmpty()) {
            categories = categoryService.search(name, PageRequest.of(page,10));
        } else {
            categories = categoryService.getAll(PageRequest.of(page,10));
        }

        model.addAttribute("categories", categories);
        ;

        return "admin/category/list";
    }

    @GetMapping("/create")
    public String showCreate(Model model){
        model.addAttribute("category", new Category());
        return "admin/category/create";
    }

    @PostMapping("/create")
    public String create(Category category){
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model){
        model.addAttribute("category", categoryService.findById(id));
        return "admin/category/edit";
    }

    @PostMapping("/edit")
    public String edit(Category category){
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        categoryService.delete(id);
        return "redirect:/admin/category";
    }
}
