package com.example.PaymentService.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PaymentService.entities.SubscriptionPayment;

@Repository
public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayment, Long> {
    SubscriptionPayment findByOrderId(String orderId);

    Optional<SubscriptionPayment> findByPaymentId(String paymentId);
}
