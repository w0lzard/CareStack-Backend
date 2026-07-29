package com.ryuken.carestack.service.impl;

import com.ryuken.carestack.dto.InventoryItemDto;
import com.ryuken.carestack.entity.InventoryItem;
import com.ryuken.carestack.repository.InventoryRepository;
import com.ryuken.carestack.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public InventoryItemDto addItem(InventoryItemDto dto) {
        InventoryItem item = new InventoryItem(null, dto.sku(), dto.medicineName(), dto.quantityOnHand(), dto.reorderThreshold());
        InventoryItem saved = inventoryRepository.save(item);
        log.info("Added new inventory item: {} ({})", saved.getSku(), saved.getId());
        return InventoryItemDto.fromEntity(saved);
    }
    @Override
    public InventoryItemDto getBySku(String sku) {
        return InventoryItemDto.fromEntity(findOrThrow(sku));
    }

    @Override
    public List<InventoryItemDto> getAllItems() {
        return inventoryRepository.findAll().stream().map(InventoryItemDto::fromEntity).toList();
    }

    @Override
    @Transactional
    public InventoryItemDto adjustQuantity(String sku, int delta) {
        InventoryItem item = findOrThrow(sku);
        item.setQuantityOnHand(item.getQuantityOnHand() + delta);
        log.info("Adjusted {} quantity by {} - new total {}", sku ,  delta , item.getQuantityOnHand());
        return InventoryItemDto.fromEntity(item);
    }

    private InventoryItem findOrThrow(String sku) {
        return inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item with SKU " + sku + " not found"));
    }
}
