package com.completebank.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.completebank.auth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByMobileNumber(String mobileNumber);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    default Optional<User> findByEmailOrMobile(String username) {

        Optional<User> user = findByEmail(username);

        if (user.isPresent()) {
            return user;
        }

        return findByMobileNumber(username);
    }
}