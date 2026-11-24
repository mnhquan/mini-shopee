package com.mnhquan.minishopee.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mnhquan.minishopee.common.response.ApiResponse;
import com.mnhquan.minishopee.dto.request.CategoryRequest;
import com.mnhquan.minishopee.dto.response.CategoryResponse;
import com.mnhquan.minishopee.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/api/v1/categories")
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        return ApiResponse.success("Category created successfully", categoryService.createCategory(request));
    }

    @GetMapping("/api/v1/categories")
    public ApiResponse<List<CategoryResponse>> getAllCategory() {
        return ApiResponse.success(categoryService.getAllCategory());
    }

    @GetMapping("/api/v1/categories/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable("id") String id) {
        return ApiResponse.success(categoryService.getCategoryById(id));
    }

    @PutMapping("/api/v1/categories/{id}")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable("id") String id,
            @RequestBody CategoryRequest request) {
        return ApiResponse.success(categoryService.updateCategory(id, request));
    }

    @DeleteMapping("/api/v1/categories/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable("id") String id) {
        categoryService.deleteCategory(id);
        return ApiResponse.success("Category has been deleted successfully", null);
    }
}
