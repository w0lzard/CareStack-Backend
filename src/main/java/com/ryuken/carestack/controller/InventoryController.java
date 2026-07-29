package com.ryuken.carestack.controller;

import com.ryuken.carestack.dto.InventoryItemDto;
import com.ryuken.carestack.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryItemDto> add(@Valid @RequestBody InventoryItemDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.addItem(dto));
    }

    @GetMapping("/{sku}")
    public ResponseEntity<InventoryItemDto> getBySku(@PathVariable String sku) {
        return ResponseEntity.ok(inventoryService.getBySku(sku));
    }

    @GetMapping
    public ResponseEntity<List<InventoryItemDto>> getAll() {
        return ResponseEntity.ok(inventoryService.getAllItems());
    }

    @PatchMapping("/{sku}/adjust")
    public ResponseEntity<InventoryItemDto> adjust(@PathVariable String sku, @RequestParam int delta) {
        return ResponseEntity.ok(inventoryService.adjustQuantity(sku, delta));
    }
}