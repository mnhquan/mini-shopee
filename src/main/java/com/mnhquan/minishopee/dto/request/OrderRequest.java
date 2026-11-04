package com.mnhquan.minishopee.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class OrderRequest {
    private String userId;
    private String shippingAddress;
    private String phoneNumber;
    private List<OrderItemRequest> items;
}
