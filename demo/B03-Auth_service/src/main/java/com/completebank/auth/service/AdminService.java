package com.completebank.auth.service;

import java.util.List;

import com.completebank.auth.dto.request.CreateUserRequest;
import com.completebank.auth.dto.response.UserResponse;

public interface AdminService {

    UserResponse createUser(CreateUserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    void deleteUser(Long id);

}