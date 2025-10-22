package com.example.InventoryService.exceptions;

public class MedicineNotFoundException extends RuntimeException {
    public MedicineNotFoundException(Long id) {
        super("Medicine not found with id: " + id);
    }
}
