package com.mnhquan.minishopee.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mnhquan.minishopee.dto.request.ProductRequest;
import com.mnhquan.minishopee.dto.response.ProductResponse;
import com.mnhquan.minishopee.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/v1/products")
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping("/api/v1/products")
    public List<ProductResponse> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/api/v1/products/{id}")
    public ProductResponse getProductById(@PathVariable("id") String id) {
        return productService.getProductById(id);
    }

    @GetMapping("/api/v1/products/category/{categoryId}")
    public List<ProductResponse> getProductByCategoryId(@PathVariable("categoryId") String categoryId) {
        return productService.getProductByCategoryId(categoryId);
    }

    @PutMapping("/api/v1/products/{id}")
    public ProductResponse updateProduct(@PathVariable("id") String id, @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/api/v1/products/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return "Product has been deleted successfully";
    }
}
