package com.mnhquan.minishopee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mnhquan.minishopee.dto.request.ProductRequest;
import com.mnhquan.minishopee.dto.response.ProductResponse;
import com.mnhquan.minishopee.entity.Category;
import com.mnhquan.minishopee.entity.Product;
import com.mnhquan.minishopee.entity.User;
import com.mnhquan.minishopee.repository.CategoryRepository;
import com.mnhquan.minishopee.repository.ProductRepository;
import com.mnhquan.minishopee.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private ProductResponse convertToResponse(Product product) {
        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setImageUrl(product.getImageUrl());
        response.setCategory(product.getCategory());
        response.setSeller(product.getSeller());
        response.setCreatedAt(product.getCreatedAt());

        return response;
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();

        Category category = categoryRepository.findById(request.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        User user = userRepository.findById(request.getSeller().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(request.getCategory());
        product.setSeller(request.getSeller());
        product.setCreatedAt(request.getCreatedAt());

        Product savedProduct = productRepository.save(product);
        return convertToResponse(savedProduct);
    }

    public List<ProductResponse> getAllProduct() {
        return productRepository.findAll().stream().map(this::convertToResponse).toList();
    }

    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return convertToResponse(product);
    }

    public List<ProductResponse> getProductByCategoryId(String categoryId) {
        return productRepository.findByCategoryId(categoryId).stream().map(this::convertToResponse).toList();
    }

    public ProductResponse updateProduct(String id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        Category category = categoryRepository.findById(request.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        User user = userRepository.findById(request.getSeller().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(request.getCategory());
        product.setSeller(request.getSeller());

        Product updatedProduct = productRepository.save(product);
        return convertToResponse(updatedProduct);
    }

    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);
        ;
    }
}
