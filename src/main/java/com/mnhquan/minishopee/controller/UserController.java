package com.mnhquan.minishopee.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mnhquan.minishopee.common.response.ApiResponse;
import com.mnhquan.minishopee.dto.request.UserRequest;
import com.mnhquan.minishopee.dto.response.UserResponse;
import com.mnhquan.minishopee.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/users")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        return ApiResponse.success("User has been created", userService.createUser(request));
    }

    @GetMapping("/api/v1/users")
    public ApiResponse<List<UserResponse>> getAllUser() {
        return ApiResponse.success(userService.getAllUsers());
    }

    @GetMapping("/api/v1/users/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable("id") String id) {
        return ApiResponse.success(userService.getUserById(id));
    }

    @PutMapping("/api/v1/users/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable("id") String id, @RequestBody UserRequest request) {
        return ApiResponse.success(userService.updateUser(id, request));
    }

    @DeleteMapping("/api/v1/users/{id}")
    public ApiResponse<String> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ApiResponse.success("User has been deleted successfully", null);
    }
}
