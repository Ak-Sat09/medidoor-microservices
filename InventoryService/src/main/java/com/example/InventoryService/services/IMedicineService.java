package com.example.InventoryService.services;

import com.example.InventoryService.dtos.MedicineRequestDto;
import com.example.InventoryService.dtos.MedicineResponseDto;
import com.example.InventoryService.entities.Medicine;

import java.util.List;

public interface IMedicineService {

    Medicine addOrUpdateMedicine(MedicineRequestDto dto);

    Medicine getMedicineById(Long id);

    Medicine reduceStock(Long id, int quantity);

    Medicine restoreStock(Long id, int quantity);

    List<MedicineResponseDto> getAllMedicinesDto();
}
