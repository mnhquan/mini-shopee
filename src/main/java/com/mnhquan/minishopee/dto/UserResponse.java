package com.mnhquan.minishopee.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mnhquan.minishopee.entity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({
        "id",
        "username",
        "email",
        "fullName",
        "phoneNumber",
        "address",
        "role",
        "createdAt"
})
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private Role role;
    private LocalDateTime createdAt;
}
