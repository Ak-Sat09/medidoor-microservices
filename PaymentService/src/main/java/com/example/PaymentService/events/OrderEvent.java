package com.example.PaymentService.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private String orderId;
    private String userId;
    private String userEmail;
    private double amount;
    private String status; // CREATED
    private Long medicineId; // new field
    private int quantity; // new field
}
