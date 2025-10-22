package com.example.OrderService.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.OrderService.entities.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findById(String orderId);

}
