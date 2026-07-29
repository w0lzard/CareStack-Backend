package com.ryuken.carestack.service;

import com.ryuken.carestack.dto.InventoryItemDto;

import java.util.List;

public interface InventoryService {
    InventoryItemDto addItem(InventoryItemDto dto);
    InventoryItemDto getBySku(String sku);
    List<InventoryItemDto> getAllItems();
    InventoryItemDto adjustQuantity(String sku, int delta);
}
