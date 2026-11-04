package com.mnhquan.minishopee.dto.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@JsonPropertyOrder({
    "id",
    "productId",
    "productName",
    "productImageUrl",
    "price",
    "quantity",
    "subTotal"
})
public class OrderItemResponse {
    private String id;
    private String productId;
    private String productName;
    private String productImageUrl;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subTotal;
}
