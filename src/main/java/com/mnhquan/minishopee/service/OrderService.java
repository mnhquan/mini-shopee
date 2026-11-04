package com.mnhquan.minishopee.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mnhquan.minishopee.dto.request.OrderRequest;
import com.mnhquan.minishopee.dto.response.OrderItemResponse;
import com.mnhquan.minishopee.dto.response.OrderResponse;
import com.mnhquan.minishopee.entity.Cart;
import com.mnhquan.minishopee.entity.CartItem;
import com.mnhquan.minishopee.entity.Order;
import com.mnhquan.minishopee.entity.OrderItem;
import com.mnhquan.minishopee.entity.OrderStatus;
import com.mnhquan.minishopee.entity.Product;
import com.mnhquan.minishopee.entity.User;
import com.mnhquan.minishopee.repository.CartRepository;
import com.mnhquan.minishopee.repository.OrderRepository;
import com.mnhquan.minishopee.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    private OrderItemResponse convertToOrderItem(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();

        response.setId(orderItem.getId());
        response.setProductId(orderItem.getProduct().getId());
        response.setProductName(orderItem.getProduct().getName());
        response.setProductImageUrl(orderItem.getProduct().getImageUrl());
        response.setPrice(orderItem.getPrice());
        response.setQuantity(orderItem.getQuantity());

        BigDecimal price = orderItem.getPrice();
        Integer quantity = (orderItem.getQuantity() != null) ? orderItem.getQuantity() : 0;
        BigDecimal subTotal = (price != null) ? price.multiply(BigDecimal.valueOf(quantity)) : BigDecimal.ZERO;

        response.setSubTotal(subTotal);
        return response;
    }

    private OrderResponse convertToOrder(Order order) {
        OrderResponse response = new OrderResponse();

        response.setId(order.getId());
        response.setUserId(order.getUser().getId());
        response.setOrderStatus(order.getOrderStatus());
        response.setShippingAddress(order.getShippingAddress());
        response.setPhoneNumber(order.getPhoneNumber());

        List<OrderItemResponse> itemResponses = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                OrderItemResponse itemResponse = convertToOrderItem(item);
                itemResponses.add(itemResponse);

                totalPrice = totalPrice.add(itemResponse.getSubTotal());
            }
        }

        response.setTotalPrice(totalPrice);
        response.setItems(itemResponses);
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }

    @Transactional
    public OrderResponse checkoutOrder(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty, cannot be checked out");
        }

        Order order = Order.builder().user(user)
                .shippingAddress(request.getShippingAddress())
                .phoneNumber(request.getPhoneNumber())
                .totalPrice(BigDecimal.ZERO).build();

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();

            if (product.getStock() < quantity) {
                throw new RuntimeException("Only " + product.getStock() + " of the " + product.getName()
                        + " are left in stock. Not enough for " + quantity);
            }

            product.setStock(product.getStock() - quantity);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .price(product.getPrice())
                    .quantity(quantity)
                    .build();

            order.getItems().add(orderItem);
            totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }

        order.setTotalPrice(totalPrice);

        cart.getItems().clear();

        Order savedOrder = orderRepository.save(order);
        return convertToOrder(savedOrder);
    }

    public List<OrderResponse> getOrdersByUserId(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderResponse> responses = new ArrayList<>();
        for (Order order : orders) {
            responses.add(convertToOrder(order));
        }
        return responses;
    }

    public OrderResponse getOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        return convertToOrder(order);
    }

    @Transactional
    public OrderResponse updateOrderStatus(String id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Cannot update a cancelled order");
        }
        if (order.getOrderStatus() == OrderStatus.DELIVERIED) {
            throw new RuntimeException("Cannot update a deliveried order");
        }

        order.setOrderStatus(newStatus);
        Order savedOrder = orderRepository.save(order);
        return convertToOrder(savedOrder);
    }

    @Transactional
    public OrderResponse cancelOrder(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Cannot cancel order with status: " + order.getOrderStatus());
        }

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        Order savedOrder = orderRepository.save(order);
        return convertToOrder(savedOrder);
    }
}
