package com.bank.notification.service;

import java.util.List;

import com.bank.notification.entity.Notification;

public interface NotificationService {

    Notification saveNotification(Notification notification);

    List<Notification> getAllNotifications();

    Notification getNotificationById(Long id);

    void deleteNotification(Long id);
}