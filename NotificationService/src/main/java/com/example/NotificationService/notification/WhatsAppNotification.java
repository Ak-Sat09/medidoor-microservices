package com.example.NotificationService.notification;

import org.springframework.stereotype.Component;

import com.example.NotificationService.dtos.NotificationDto;

@Component("whatsapp")
public class WhatsAppNotification implements Notification {

    @Override
    public void sendNotification(NotificationDto dto) {
        System.out.println("Sending WhatsApp message to " + dto.getRecipient() + " with message: " + dto.getMessage());
    }
}
