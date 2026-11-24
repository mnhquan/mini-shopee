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
import com.mnhquan.minishopee.dto.request.ProductRequest;
import com.mnhquan.minishopee.dto.response.ProductResponse;
import com.mnhquan.minishopee.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/v1/products")
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        return ApiResponse.success("Product created successfully", productService.createProduct(request));
    }

    @GetMapping("/api/v1/products")
    public ApiResponse<List<ProductResponse>> getAllProduct() {
        return ApiResponse.success(productService.getAllProduct());
    }

    @GetMapping("/api/v1/products/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable("id") String id) {
        return ApiResponse.success(productService.getProductById(id));
    }

    @GetMapping("/api/v1/products/category/{categoryId}")
    public ApiResponse<List<ProductResponse>> getProductByCategoryId(@PathVariable("categoryId") String categoryId) {
        return ApiResponse.success(productService.getProductByCategoryId(categoryId));
    }

    @PutMapping("/api/v1/products/{id}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable("id") String id, @RequestBody ProductRequest request) {
        return ApiResponse.success(productService.updateProduct(id, request));
    }

    @DeleteMapping("/api/v1/products/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return ApiResponse.success("Product has been deleted successfully", null);
    }
}
