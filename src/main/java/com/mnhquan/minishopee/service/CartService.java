package com.mnhquan.minishopee.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mnhquan.minishopee.dto.request.CartItemRequest;
import com.mnhquan.minishopee.dto.response.CartItemResponse;
import com.mnhquan.minishopee.dto.response.CartResponse;
import com.mnhquan.minishopee.entity.Cart;
import com.mnhquan.minishopee.entity.CartItem;
import com.mnhquan.minishopee.entity.Product;
import com.mnhquan.minishopee.entity.User;
import com.mnhquan.minishopee.repository.CartRepository;
import com.mnhquan.minishopee.repository.ProductRepository;
import com.mnhquan.minishopee.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private CartItemResponse convertToItemResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();

        response.setId(cartItem.getId());
        response.setProductId(cartItem.getProduct().getId());
        response.setProductName(cartItem.getProduct().getName());
        response.setProductImageUrl(cartItem.getProduct().getImageUrl());
        response.setPrice(cartItem.getProduct().getPrice());
        response.setQuantity(cartItem.getQuantity());

        BigDecimal price = cartItem.getProduct().getPrice();
        int quantity = (cartItem.getQuantity() != null) ? cartItem.getQuantity() : 0;
        BigDecimal subTotal = (price != null) ? price.multiply(BigDecimal.valueOf(quantity)) : BigDecimal.ZERO;
        response.setSubTotal(subTotal);

        return response;
    }

    private CartResponse convertToResponse(Cart cart) {
        CartResponse response = new CartResponse();

        response.setId(cart.getId());
        response.setUserId(cart.getUser().getId());

        List<CartItemResponse> itemResponses = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        if (cart.getItems() != null) {
            for (CartItem item : cart.getItems()) {
                CartItemResponse itemResponse = convertToItemResponse(item);
                itemResponses.add(itemResponse);

                totalPrice = totalPrice.add(itemResponse.getSubTotal());
            }
        }

        response.setItems(itemResponses);
        response.setTotalPrice(totalPrice);

        return response;
    }

    public Cart getOrCreateCart(String userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

            Cart newCart = Cart.builder()
                    .user(user)
                    .items(new ArrayList<>())
                    .build();

            return cartRepository.save(newCart);
        });
    }

    public CartResponse getCartByUserId(String userId) {
        Cart cart = getOrCreateCart(userId);
        return convertToResponse(cart);
    }

    public CartResponse addToCart(String userId, CartItemRequest request) {
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new RuntimeException("Invalid quantity added to cart");
        }

        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        int targetQuantity = (existingItem != null)
                ? existingItem.getQuantity() + request.getQuantity()
                : request.getQuantity();

        if (targetQuantity > product.getStock()) {
            throw new RuntimeException("The quantity exceeds the current inventory (" + product.getStock() + ")");
        }

        if (existingItem != null) {
            existingItem.setQuantity(targetQuantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(targetQuantity);
            cart.getItems().add(newItem);
        }

        Cart savedCart = cartRepository.save(cart);
        return convertToResponse(savedCart);
    }

    public CartResponse updateCartItemQuantity(String userId, String cartItemId, Integer newQuantity) {
        if (newQuantity <= 0) {
            throw new RuntimeException("The quantity must be greater than 0");
        }

        Cart cart = getOrCreateCart(userId);
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        if (newQuantity > item.getProduct().getStock()) {
            throw new RuntimeException(
                    "The quantity exceeds the current inventory (" + item.getProduct().getStock() + ")");
        }

        item.setQuantity(newQuantity);
        Cart savedCart = cartRepository.save(cart);
        return convertToResponse(savedCart);
    }

    public CartResponse removeCartItem(String userId, String cartItemId) {
        Cart cart = getOrCreateCart(userId);
        boolean removed = cart.getItems().removeIf(i -> i.getId().equals(cartItemId));

        if (!removed) {
            throw new RuntimeException("Item is not existed in cart");
        }

        Cart savedCart = cartRepository.save(cart);
        return convertToResponse(savedCart);
    }
}
