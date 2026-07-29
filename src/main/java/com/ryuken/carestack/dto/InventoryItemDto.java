package com.ryuken.carestack.dto;

import com.ryuken.carestack.entity.InventoryItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record InventoryItemDto(
        Long id,
        @NotBlank String sku,
        @NotBlank String medicineName,
        @Min(0) int quantityOnHand,
        @Min(0) int reorderThreshold
) {
    public static InventoryItemDto fromEntity(InventoryItem i) {
        return new InventoryItemDto(i.getId(), i.getSku(), i.getMedicineName(), i.getQuantityOnHand(), i.getReorderThreshold());
    }
}
