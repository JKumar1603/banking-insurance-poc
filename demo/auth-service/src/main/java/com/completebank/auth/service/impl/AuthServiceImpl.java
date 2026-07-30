package com.completebank.auth.service.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.completebank.auth.dto.request.ForgotPasswordRequest;
import com.completebank.auth.dto.request.LoginRequest;
import com.completebank.auth.dto.request.RefreshTokenRequest;
import com.completebank.auth.dto.request.RegisterRequest;
import com.completebank.auth.dto.response.AuthResponse;
import com.completebank.auth.dto.response.MessageResponse;
import com.completebank.auth.dto.response.UserResponse;
import com.completebank.auth.entity.Role;
import com.completebank.auth.entity.User;
import com.completebank.auth.enums.RoleName;
import com.completebank.auth.enums.UserStatus;
import com.completebank.auth.exception.ResourceAlreadyExistsException;
import com.completebank.auth.exception.ResourceNotFoundException;
import com.completebank.auth.repository.RoleRepository;
import com.completebank.auth.repository.UserRepository;
import com.completebank.auth.security.JwtService;
import com.completebank.auth.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public MessageResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered.");
        }

        if (userRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new ResourceAlreadyExistsException("Mobile number already registered.");
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setEnabled(true);

        Role customerRole = roleRepository
                .findByRoleName(RoleName.CUSTOMER)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer role not found."));

        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);

        user.setRoles(roles);

        userRepository.save(user);

        return MessageResponse.success("User registered successfully.");
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        User user = userRepository
                .findByEmailOrMobile(request.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        return buildAuthResponse(user);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        String refreshToken = request.getRefreshToken();

        String username = jwtService.extractUsername(refreshToken);

        User user = userRepository
                .findByEmailOrMobile(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        if (!jwtService.isTokenValid(refreshToken, user.getEmail())) {
            throw new RuntimeException("Invalid or expired refresh token.");
        }

        return buildAuthResponse(user);
    }

    @Override
    public MessageResponse forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        return MessageResponse.success("Password updated successfully.");
    }

    private AuthResponse buildAuthResponse(User user) {

        String accessToken = jwtService.generateAccessToken(user.getEmail());

        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setMobileNumber(user.getMobileNumber());
        userResponse.setStatus(user.getStatus().name());
        userResponse.setEnabled(user.isEnabled());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());

        userResponse.setRoles(
                user.getRoles()
                        .stream()
                        .map(role -> role.getRoleName().name())
                        .collect(Collectors.toSet()));

        return new AuthResponse(
                true,
                "Authentication Successful",
                accessToken,
                refreshToken,
                "Bearer",
                3600000,
                userResponse
        );
    }
}