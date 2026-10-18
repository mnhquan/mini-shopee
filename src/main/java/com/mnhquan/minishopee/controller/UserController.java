package com.mnhquan.minishopee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mnhquan.minishopee.dto.UserRequest;
import com.mnhquan.minishopee.dto.UserResponse;
import com.mnhquan.minishopee.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("api/v1/users")
    public UserResponse createUser(@RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("api/v1/users")
    public List<UserResponse> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("api/v1/users/{id}")
    public UserResponse getUserById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    @PutMapping("api/v1/users/{id}")
    public UserResponse updateUser(@PathVariable("id") String id, @RequestBody UserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("api/v1/users/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return "User has been deleted successfully";
    }
}
