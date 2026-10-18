package com.mnhquan.minishopee.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mnhquan.minishopee.dto.CategoryRequest;
import com.mnhquan.minishopee.dto.CategoryResponse;
import com.mnhquan.minishopee.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/api/v1/categories")
    public CategoryResponse createCategory(@RequestBody CategoryRequest request) {
        return categoryService.createCategory(request);
    }

    @GetMapping("/api/v1/categories")
    public List<CategoryResponse> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/api/v1/categories/{id}")
    public CategoryResponse getCategoryById(@PathVariable("id") String id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/api/v1/categories/{id}")
    public CategoryResponse updateCategory(@PathVariable("id") String id, @RequestBody CategoryRequest request) {
        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping("/api/v1/categories/{id}")
    public String deleteCategory(@PathVariable("id") String id) {
        categoryService.deleteCategory(id);
        return "Category has been deleted succesfully";
    }
}