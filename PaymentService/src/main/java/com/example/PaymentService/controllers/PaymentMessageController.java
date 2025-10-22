package com.example.PaymentService.controllers;

import java.util.Map;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.PaymentService.events.OrderEvent;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PaymentMessageController {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Called by Kafka consumer after creating Razorpay order
     */
    public void sendPaymentOrder(OrderEvent order, String razorpayOrderId, int amountInPaise, String keyId) {
        Map<String, Object> orderData = Map.of(
                "orderId", razorpayOrderId,
                "amount", amountInPaise,
                "currency", "INR",
                "userId", order.getUserId(),
                "userEmail", order.getUserEmail(),
                "key", keyId);

        // Send to all subscribers on /topic/payments
        messagingTemplate.convertAndSend("/topic/payments", orderData);
    }
}
