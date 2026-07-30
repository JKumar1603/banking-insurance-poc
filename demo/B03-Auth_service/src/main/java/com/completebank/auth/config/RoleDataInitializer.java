package com.completebank.auth.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.completebank.auth.entity.Role;
import com.completebank.auth.enums.RoleName;
import com.completebank.auth.repository.RoleRepository;

@Component
public class RoleDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleDataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        saveRole(RoleName.CUSTOMER, "Customer Role");
        saveRole(RoleName.STAFF, "Bank Staff");
        saveRole(RoleName.ADMIN, "Administrator");
        saveRole(RoleName.AUDITOR, "Auditor");
    }

    private void saveRole(RoleName roleName, String description) {

        if (!roleRepository.existsByRoleName(roleName)) {

            Role role = new Role();
            role.setRoleName(roleName);
            role.setDescription(description);

            roleRepository.save(role);
        }
    }
}