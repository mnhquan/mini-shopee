package com.mnhquan.minishopee.dto.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder ({
    "id",
    "userId",
    "items",
    "totalPrice"
})
public class CartResponse {
    private String id;
    private String userId;
    private List<CartItemResponse> items = new ArrayList<>();
    private BigDecimal totalPrice;
}
