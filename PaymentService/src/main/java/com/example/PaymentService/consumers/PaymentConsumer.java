package com.example.PaymentService.consumers;

import com.example.PaymentService.config.RazorpayConfig;
import com.example.PaymentService.config.RazorpayWrapper;
import com.example.PaymentService.controllers.PaymentMessageController;
import com.example.PaymentService.events.OrderEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentConsumer {

    private final RazorpayWrapper razorpayWrapper;
    private final RazorpayConfig razorpayConfig;
    private final PaymentMessageController paymentMessageController;

    @KafkaListener(topics = "order-topic", groupId = "order-group")
    public void consumeOrder(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            OrderEvent order = mapper.readValue(message, OrderEvent.class);

            System.out.println("Received order: " + order);

            // ✅ Create Razorpay order
            int amountInPaise = (int) (order.getAmount() * 100);

            // Truncate receiptId to max 40 chars
            String rawReceipt = "rcpt_" + order.getUserId() + "_" + order.getOrderId();
            String receiptId = rawReceipt.length() > 40 ? rawReceipt.substring(0, 40) : rawReceipt;

            com.razorpay.Order razorpayOrder = razorpayWrapper.createOrder(amountInPaise, "INR", receiptId);

            System.out.println("Razorpay Order created: " + razorpayOrder.get("id"));

            // ✅ Send STOMP message to frontend
            paymentMessageController.sendPaymentOrder(
                    order,
                    razorpayOrder.get("id"),
                    amountInPaise,
                    razorpayConfig.getKeyId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
