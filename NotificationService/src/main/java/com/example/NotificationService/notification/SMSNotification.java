package com.example.NotificationService.notification;

import org.springframework.stereotype.Component;

@Component("sms")
public class SMSNotification implements Notification {
    @Override
    public void sendNotification(com.example.NotificationService.dtos.NotificationDto dto) {
        System.out.println("Sending SMS to " + dto.getRecipient() + " with message: " + dto.getMessage());
    }

}
