package com.mnhquan.minishopee.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class OrderItemRequest {
    private String productId;
    private Integer quantity;
}
