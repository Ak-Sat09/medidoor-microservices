package com.example.InventoryService.services;

import com.example.InventoryService.entities.Medicine;

@FunctionalInterface
public interface StockOperation {
    Medicine apply(Medicine medicine, int quantity);
}
