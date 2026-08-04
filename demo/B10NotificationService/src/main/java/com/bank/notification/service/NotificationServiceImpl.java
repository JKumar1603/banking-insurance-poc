package com.bank.notification.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.notification.entity.Notification;
import com.bank.notification.entity.OtpDetails;
import com.bank.notification.repository.NotificationRepository;
import com.bank.notification.repository.OtpRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public Notification saveNotification(Notification notification) {
        return repository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    @Override
    public Notification getNotificationById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteNotification(Long id) {
        repository.deleteById(id);
    }

    public String generateOtp(String email) {

        String otp =
                String.valueOf(
                        100000 + new Random().nextInt(900000));

        OtpDetails details =
                otpRepository.findByEmail(email);

        if (details == null) {
            details = new OtpDetails();
        }

        details.setEmail(email);
        details.setOtp(otp);

        otpRepository.save(details);

        return otp;
    }

    public String verifyOtp(String email,
                            String otp) {

        OtpDetails details =
                otpRepository.findByEmail(email);

        if (details == null) {
            return "OTP Not Found";
        }

        if (details.getOtp().equals(otp)) {
            return "OTP Verified Successfully";
        }

        return "Invalid OTP";
    }
}