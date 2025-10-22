package com.example.OrderService.events;

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
    private String status;
    private Long medicineId;
    private int quantity;
}
