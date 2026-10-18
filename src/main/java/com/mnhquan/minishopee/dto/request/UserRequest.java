package com.mnhquan.minishopee.dto.request;

import com.mnhquan.minishopee.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private Role role;
}
