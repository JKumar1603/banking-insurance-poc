package com.completebank.auth.service;

import com.completebank.auth.dto.request.ForgotPasswordRequest;
import com.completebank.auth.dto.request.LoginRequest;
import com.completebank.auth.dto.request.RefreshTokenRequest;
import com.completebank.auth.dto.request.RegisterRequest;
import com.completebank.auth.dto.response.AuthResponse;
import com.completebank.auth.dto.response.MessageResponse;

public interface AuthService {

    MessageResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

    MessageResponse forgotPassword(ForgotPasswordRequest request);
}