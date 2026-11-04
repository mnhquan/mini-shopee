package com.mnhquan.minishopee.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mnhquan.minishopee.dto.request.OrderRequest;
import com.mnhquan.minishopee.dto.response.OrderResponse;
import com.mnhquan.minishopee.entity.OrderStatus;
import com.mnhquan.minishopee.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/checkout")
    public OrderResponse checkoutOrder(@RequestParam("userId") String userId, @RequestBody OrderRequest request) {
        return orderService.checkoutOrder(request);
    }

    @GetMapping("/api/v1/orders/user/{userId}")
    public List<OrderResponse> getOrdersByUserId(@PathVariable("userId") String userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("/api/v1/orders/{id}")
    public OrderResponse getOrderById(@PathVariable("id") String id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/api/v1/orders/{id}/status")
    public OrderResponse updateOrderStatus(@PathVariable("id") String id,
            @RequestParam("status") OrderStatus newStatus) {
        return orderService.updateOrderStatus(id, newStatus);
    }

    @PostMapping("/api/v1/orders/{id}/cancel")
    public OrderResponse cancelOrder(@PathVariable("id") String id) {
        return orderService.cancelOrder(id);
    }
}
