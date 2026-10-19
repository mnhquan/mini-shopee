package com.mnhquan.minishopee.dto.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponse {
    private String id;
    private String userId;
    private List<CartItemResponse> items = new ArrayList<>();
    private BigDecimal totalPrice;
}
