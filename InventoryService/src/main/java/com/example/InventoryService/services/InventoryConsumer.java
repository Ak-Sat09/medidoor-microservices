package com.example.InventoryService.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

@Service
public class InventoryConsumer {

    private final IMedicineService medicineService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public InventoryConsumer(IMedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @KafkaListener(topics = "order-topic", groupId = "inventory-group")
    public void consumeOrderEvent(String message) {
        try {
            // Parse JSON string into Map
            Map<String, Object> order = objectMapper.readValue(message, new TypeReference<>() {
            });

            Long medicineId = Long.valueOf(order.get("medicineId").toString());
            int quantity = Integer.parseInt(order.get("quantity").toString());

            System.out.println(" InventoryService received OrderEvent: " + order);

            // Reduce stock no matter the status
            medicineService.reduceStock(medicineId, quantity);
            System.out.println(" Reduced stock for medicine ID " + medicineId + " by quantity " + quantity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
