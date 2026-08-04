package com.bank.notification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bank.notification.dto.OtpRequest;
import com.bank.notification.entity.Notification;
import com.bank.notification.service.NotificationService;
import com.bank.notification.service.NotificationServiceImpl;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @PostMapping("/send")
    public Notification sendNotification(
            @RequestBody Notification notification) {

        notification.setStatus("SENT");

        return service.saveNotification(notification);
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return service.getAllNotifications();
    }

    @GetMapping("/{id}")
    public Notification getNotificationById(
            @PathVariable Long id) {

        return service.getNotificationById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteNotification(
            @PathVariable Long id) {

        service.deleteNotification(id);

        return "Notification Deleted Successfully";
    }

    @PostMapping("/send-sms")
    public String sendSms(
            @RequestBody Notification notification) {

        System.out.println(
                "SMS Sent To : "
                + notification.getRecipient()
                + " Message : "
                + notification.getMessage());

        return "SMS Sent Successfully";
    }

    @PostMapping("/generate-otp")
    public String generateOtp(
            @RequestBody OtpRequest request) {

        return notificationServiceImpl.generateOtp(
                request.getEmail());
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(
            @RequestBody OtpRequest request) {

        return notificationServiceImpl.verifyOtp(
                request.getEmail(),
                request.getOtp());
    }

    @PostMapping("/transaction-alert")
    public Notification transactionAlert(
            @RequestBody Notification notification) {

        notification.setNotificationType("ALERT");
        notification.setStatus("SENT");

        return service.saveNotification(notification);
    }
}