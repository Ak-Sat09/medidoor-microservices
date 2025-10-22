package com.example.InventoryService.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.InventoryService.dtos.MedicineRequestDto;
import com.example.InventoryService.dtos.MedicineResponseDto;
import com.example.InventoryService.entities.Medicine;
import com.example.InventoryService.exceptions.MedicineNotFoundException;
import com.example.InventoryService.mapper.MedicineMapper;
import com.example.InventoryService.repositories.MedicineRepository;

import jakarta.transaction.Transactional;

@Service
public class MedicineService implements IMedicineService {

    private final MedicineRepository repository;

    public MedicineService(MedicineRepository repository) {
        this.repository = repository;
    }

    // Add new medicine or increase stock if exists
    @Override
    @Transactional
    public Medicine addOrUpdateMedicine(MedicineRequestDto dto) {
        Medicine medicine = MedicineMapper.toEntity(dto);
        return repository.findByName(medicine.getName())
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + medicine.getQuantity());
                    return repository.save(existing);
                })
                .orElseGet(() -> repository.save(medicine));
    }

    // Get medicine by ID (for delivery)
    @Override
    public Medicine getMedicineById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MedicineNotFoundException(id));
    }

    // Reduce stock (delivery)
    @Override
    @Transactional
    public Medicine reduceStock(Long id, int quantity) {
        return changeStock(id, quantity, (med, qty) -> {
            med.setQuantity(Math.max(med.getQuantity() - qty, 0));
            return med;
        });
    }

    // Restore stock (delivery failed)
    @Override
    @Transactional
    public Medicine restoreStock(Long id, int quantity) {
        return changeStock(id, quantity, (med, qty) -> {
            med.setQuantity(med.getQuantity() + qty);
            return med;
        });
    }

    // List all medicines (optional for UI or other services)
    @Override
    public List<MedicineResponseDto> getAllMedicinesDto() {
        return repository.findAll()
                .stream()
                .map(MedicineMapper::toDto)
                .collect(Collectors.toList());
    }

    // Reusable method for stock operations (Functional interface pattern)
    private Medicine changeStock(Long id, int quantity, StockOperation operation) {
        Medicine med = repository.findById(id)
                .orElseThrow(() -> new MedicineNotFoundException(id));
        med = operation.apply(med, quantity);
        return repository.save(med);
    }
}
