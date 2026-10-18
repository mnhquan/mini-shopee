package com.mnhquan.minishopee.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({
        "id",
        "name",
        "description"
})
public class CategoryResponse {
    private String id;
    private String name;
    private String description;
}
