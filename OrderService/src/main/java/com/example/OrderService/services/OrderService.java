package com.example.OrderService.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.OrderService.entities.Order;
import com.example.OrderService.events.OrderEvent;
import com.example.OrderService.producers.OrderProducer;
import com.example.OrderService.repositories.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final OrderProducer producer;

    public OrderService(OrderRepository repository, OrderProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public Order placeOrder(Order order) {
        // 1️⃣ Generate unique order ID
        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus("CREATED");

        // 2️⃣ Save order in DB
        Order savedOrder = repository.save(order);

        // 3️⃣ Publish OrderCreatedEvent to Kafka
        OrderEvent event = new OrderEvent(
                savedOrder.getOrderId(),
                savedOrder.getUserId(),
                savedOrder.getUserEmail(), savedOrder.getAmount(),
                savedOrder.getStatus(),
                savedOrder.getMedicineId(),
                savedOrder.getQuantity());

        producer.sendOrderEvent(event);

        return savedOrder;
    }

    public Order markOrderSuccess(String orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus("SUCCESS");
        Order updatedOrder = repository.save(order);

        // Publish SUCCESS event
        OrderEvent event = new OrderEvent(
                updatedOrder.getOrderId().toString(),
                updatedOrder.getUserId(),
                updatedOrder.getUserEmail(),
                updatedOrder.getAmount(),
                updatedOrder.getStatus(),
                updatedOrder.getMedicineId(),
                updatedOrder.getQuantity());

        producer.sendOrderEvent(event); // same Kafka topic

        return updatedOrder;
    }

}
