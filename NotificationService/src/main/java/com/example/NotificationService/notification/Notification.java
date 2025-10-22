package com.example.NotificationService.notification;

import com.example.NotificationService.dtos.NotificationDto;

public interface Notification {

    void sendNotification(NotificationDto dto);

}