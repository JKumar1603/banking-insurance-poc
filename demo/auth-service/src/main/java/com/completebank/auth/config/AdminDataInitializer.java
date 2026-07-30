package com.completebank.auth.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.completebank.auth.entity.Role;
import com.completebank.auth.entity.User;
import com.completebank.auth.enums.RoleName;
import com.completebank.auth.enums.UserStatus;
import com.completebank.auth.repository.RoleRepository;
import com.completebank.auth.repository.UserRepository;

@Component
public class AdminDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminDataInitializer(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // Create the default admin only once
        if (userRepository.existsByEmail("admin@completebank.com")) {
            return;
        }

        Role adminRole = roleRepository.findByRoleName(RoleName.ADMIN)
                .orElseThrow(() ->
                        new RuntimeException("ADMIN role not found."));

        User admin = new User();

        admin.setFirstName("System");
        admin.setLastName("Administrator");
        admin.setEmail("admin@completebank.com");
        admin.setMobileNumber("9999999999");
        admin.setPassword(passwordEncoder.encode("Admin@123"));

        admin.setStatus(UserStatus.ACTIVE);
        admin.setEnabled(true);

        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);

        admin.setRoles(roles);

        userRepository.save(admin);

        System.out.println();
        System.out.println("==========================================");
        System.out.println(" DEFAULT ADMIN CREATED SUCCESSFULLY");
        System.out.println(" Email    : admin@completebank.com");
        System.out.println(" Password : Admin@123");
        System.out.println("==========================================");
        System.out.println();
    }
}