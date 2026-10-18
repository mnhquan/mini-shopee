package com.mnhquan.minishopee.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mnhquan.minishopee.entity.Category;
import com.mnhquan.minishopee.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private Category category;
    private User seller;
    private LocalDateTime createdAt;
}
