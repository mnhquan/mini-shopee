package com.mnhquan.minishopee.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mnhquan.minishopee.common.response.ApiResponse;
import com.mnhquan.minishopee.dto.request.CartItemRequest;
import com.mnhquan.minishopee.dto.response.CartResponse;
import com.mnhquan.minishopee.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/api/v1/cart")
    public ApiResponse<CartResponse> getCartByUserId(@RequestParam("userId") String userId) {
        return ApiResponse.success(cartService.getCartByUserId(userId));
    }

    @PostMapping("/api/v1/cart/items")
    public ApiResponse<CartResponse> addToCart(@RequestParam("userId") String userId,
            @RequestBody CartItemRequest request) {
        return ApiResponse.success("Item added to cart successfully", cartService.addToCart(userId, request));
    }

    @PutMapping("/api/v1/cart/items/{itemId}")
    public ApiResponse<CartResponse> updateCartItemQuantity(@PathVariable("itemId") String itemId,
            @RequestParam("userId") String userId,
            @RequestBody CartItemRequest request) {
        return ApiResponse.success("Cart item quantity updated successfully", cartService.updateCartItemQuantity(userId, itemId, request.getQuantity()));
    }

@DeleteMapping("/api/v1/cart/items/{itemId}")
    public ApiResponse<CartResponse> removeCartItem(@PathVariable("itemId") String itemId, @RequestParam("userId") String userId) {
        return ApiResponse.success("Item removed from cart successfully", cartService.removeCartItem(userId, itemId));
    }
}
