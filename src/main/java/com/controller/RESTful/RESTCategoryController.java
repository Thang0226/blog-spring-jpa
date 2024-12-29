package com.controller.RESTful;

import com.model.Category;
import com.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class RESTCategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public Iterable<Category> getAllCategories() {
        return categoryService.findAll();
    }
}
