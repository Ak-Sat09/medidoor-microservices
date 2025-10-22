package com.example.InventoryService.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockEvent {
    private Long medicineId;
    private int quantity;
    private EventType eventType;

    public enum EventType {
        REDUCE, RESTORE, RESERVE, RELEASE_RESERVED
    }
}
