package com.example.InventoryService.mapper;

import com.example.InventoryService.dtos.MedicineRequestDto;
import com.example.InventoryService.dtos.MedicineResponseDto;
import com.example.InventoryService.entities.Medicine;

public class MedicineMapper {

    public static Medicine toEntity(MedicineRequestDto dto) {
        if (dto == null)
            return null;
        Medicine med = new Medicine();
        med.setName(dto.getName());
        med.setType(dto.getType());
        med.setPrice(dto.getPrice());
        med.setQuantity(dto.getQuantity());
        med.setBatchNumber(dto.getBatchNumber());
        med.setExpiryDate(dto.getExpiryDate());
        return med;
    }

    // Convert Entity → Response DTO
    public static MedicineResponseDto toDto(Medicine med) {
        if (med == null)
            return null;
        MedicineResponseDto dto = new MedicineResponseDto();
        dto.setId(med.getId());
        dto.setName(med.getName());
        dto.setType(med.getType());
        dto.setPrice(med.getPrice());
        dto.setQuantity(med.getQuantity());
        dto.setBatchNumber(med.getBatchNumber());
        dto.setExpiryDate(med.getExpiryDate());
        return dto;
    }
}
