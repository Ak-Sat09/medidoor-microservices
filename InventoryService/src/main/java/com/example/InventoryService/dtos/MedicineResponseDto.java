package com.example.InventoryService.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineResponseDto {
    private Long id;
    private String name;
    private String type;
    private double price;
    private int quantity;
    private String batchNumber;
    private LocalDate expiryDate;
}
