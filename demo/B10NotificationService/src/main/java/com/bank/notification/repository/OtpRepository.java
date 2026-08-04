package com.bank.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.notification.entity.OtpDetails;

public interface OtpRepository extends JpaRepository<OtpDetails, Long> {

    OtpDetails findByEmail(String email);

}