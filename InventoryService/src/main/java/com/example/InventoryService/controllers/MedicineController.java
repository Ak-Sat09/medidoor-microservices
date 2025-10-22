package com.example.InventoryService.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.InventoryService.dtos.MedicineRequestDto;
import com.example.InventoryService.dtos.MedicineResponseDto;
import com.example.InventoryService.entities.Medicine;
import com.example.InventoryService.mapper.MedicineMapper;
import com.example.InventoryService.services.IMedicineService;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private final IMedicineService medicineService;

    public MedicineController(IMedicineService medicineService) {
        this.medicineService = medicineService;
    }

    // Add or update medicine (increase stock)
    @PostMapping
    public ResponseEntity<MedicineResponseDto> addMedicine(@RequestBody MedicineRequestDto dto) {
        Medicine med = medicineService.addOrUpdateMedicine(dto);
        return ResponseEntity.ok(MedicineMapper.toDto(med));
    }

    // Get medicine by ID (for delivery or checking stock)
    @GetMapping("/{id}")
    public ResponseEntity<MedicineResponseDto> getMedicine(@PathVariable Long id) {
        Medicine med = medicineService.getMedicineById(id);
        return ResponseEntity.ok(MedicineMapper.toDto(med));
    }

    // Reduce stock (delivery)
    @PostMapping("/{id}/reduce-stock")
    public ResponseEntity<MedicineResponseDto> reduceStock(@PathVariable Long id,
            @RequestParam int quantity) {
        Medicine med = medicineService.reduceStock(id, quantity);
        return ResponseEntity.ok(MedicineMapper.toDto(med));
    }

    // Restore stock (delivery failed)
    @PostMapping("/{id}/restore-stock")
    public ResponseEntity<MedicineResponseDto> restoreStock(@PathVariable Long id,
            @RequestParam int quantity) {
        Medicine med = medicineService.restoreStock(id, quantity);
        return ResponseEntity.ok(MedicineMapper.toDto(med));
    }

    // List all medicines (optional for dashboard/UI)
    @GetMapping
    public ResponseEntity<List<MedicineResponseDto>> getAllMedicines() {
        List<MedicineResponseDto> medicines = medicineService.getAllMedicinesDto();
        return ResponseEntity.ok(medicines);
    }
}