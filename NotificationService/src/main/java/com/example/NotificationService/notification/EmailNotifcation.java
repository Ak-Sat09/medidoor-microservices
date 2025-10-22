package com.example.NotificationService.notification;

import org.springframework.stereotype.Component;

import com.example.NotificationService.dtos.NotificationDto;

@Component("email")
public class EmailNotifcation implements Notification {
    @Override
    public void sendNotification(NotificationDto dto) {
        System.out.println("Sending Email to " + dto.getRecipient() + " with message: " + dto.getMessage());
    }
}
