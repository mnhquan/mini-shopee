package com.mnhquan.minishopee.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mnhquan.minishopee.entity.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@JsonPropertyOrder({
    "id",
    "userId",
    "orderStatus",
    "totalPrice",
    "shippingAddress",
    "phoneNumber",
    "createdAt",
    "items"
})
public class OrderResponse {
    private String id;
    private String userId;
    private OrderStatus orderStatus;
    private String shippingAddress;
    private String phoneNumber;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}
