package com.example.NotificationService.consumers;

import com.example.NotificationService.services.EmailService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class NotificationConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EmailService emailService;

    public NotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "order-topic", groupId = "notification-group")
    public void consumeNotification(String message) {
        try {
            Map<String, Object> order = objectMapper.readValue(message, new TypeReference<>() {
            });

            // ✅ Extract all fields
            String userEmail = (String) order.get("userEmail");
            String orderId = String.valueOf(order.get("orderId"));
            String userId = String.valueOf(order.get("userId"));
            String medicineId = String.valueOf(order.get("medicineId"));
            String amount = String.valueOf(order.get("amount"));
            String quantity = String.valueOf(order.get("quantity"));
            String status = String.valueOf(order.get("status"));

            // ✅ Create detailed email body
            String body = "Dear User,\n\nYour order update details are below:\n\n" +
                    "Order ID: " + orderId + "\n" +
                    "User ID: " + userId + "\n" +
                    "Medicine ID: " + medicineId + "\n" +
                    "Quantity: " + quantity + "\n" +
                    "Amount: ₹" + amount + "\n" +
                    "Status: " + status + "\n\n" +
                    "Thank you for using our service!\n\n- Team";

            // ✅ Send email
            emailService.sendEmail(userEmail, "Order Update Notification", body);

            System.out.println("✅ Email sent to: " + userEmail);

        } catch (Exception e) {
            System.out.println("❌ Error processing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
