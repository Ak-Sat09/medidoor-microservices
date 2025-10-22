package com.example.PaymentService.services;

import org.springframework.stereotype.Service;

import com.example.PaymentService.config.RazorpayWrapper;
import com.example.PaymentService.entities.PaymentStatus;
import com.example.PaymentService.entities.SubscriptionPayment;
import com.example.PaymentService.repositories.SubscriptionPaymentRepository;
import com.razorpay.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final SubscriptionPaymentRepository paymentRepo;
    private final RazorpayWrapper razorpayWrapper;

    public void processPayment(String orderId, String userId, double amount) {
        try {
            int amountInPaise = (int) (amount * 100);
            String receiptId = "rcpt_" + userId + "_" + orderId;

            Order razorpayOrder = razorpayWrapper.createOrder(amountInPaise, "INR", receiptId);

            SubscriptionPayment payment = SubscriptionPayment.builder()
                    .orderId(razorpayOrder.get("id"))
                    .userId(Long.parseLong(userId))
                    .planId(0L)
                    .amount(amountInPaise)
                    .status(PaymentStatus.PENDING)
                    .build();
            paymentRepo.save(payment);

            System.out.println("Payment processed for order: " + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Payment failed for order: " + orderId);
        }
    }
}
