package com.mnhquan.minishopee.dto.request;

import com.mnhquan.minishopee.common.exception.ErrorCode;
import com.mnhquan.minishopee.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    private String username;

    @Email(message = "Email format is wrong")
    private String email;

    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private Role role;
}
