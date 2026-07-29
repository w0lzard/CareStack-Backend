package com.ryuken.carestack.scheduler;

import com.ryuken.carestack.dto.SupplierStockLevel;
import com.ryuken.carestack.entity.InventoryItem;
import com.ryuken.carestack.entity.ReconciliationDiscrepancy;
import com.ryuken.carestack.service.SupplierStockClient;
import com.ryuken.carestack.repository.InventoryRepository;
import com.ryuken.carestack.repository.ReconciliationDiscrepancyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryReconciliationJob {

    private final InventoryRepository inventoryRepository;
    private final SupplierStockClient supplierStockClient;
    private final ReconciliationDiscrepancyRepository discrepancyRepository;

    @Scheduled(cron = "${hospital.scheduling.inventory-reconciliation-cron}")
    @Transactional
    public void reconcile() {
        log.info("Starting nightly inventory reconciliation");

        List<InventoryItem> localItems = inventoryRepository.findAll();
        Map<String, Integer> supplierLevelsBySku = supplierStockClient.fetchSupplierStock().stream()
                .collect(java.util.stream.Collectors.toMap(
                        SupplierStockLevel::sku, SupplierStockLevel::quantityAtSupplier));

        int discrepancyCount = 0;
        for (InventoryItem local : localItems) {
            Integer supplierQty = supplierLevelsBySku.get(local.getSku());
            if (supplierQty == null) {
                log.warn("SKU {} not found in supplier feed - skipping", local.getSku());
                continue;
            }
            if (supplierQty != local.getQuantityOnHand()) {
                logDiscrepancy(local, supplierQty);
                discrepancyCount++;
            }
        }

        log.info("Inventory reconciliation complete - {} discrepancy(ies) found across {} item(s)",
                discrepancyCount, localItems.size());
    }

    private void logDiscrepancy(InventoryItem local, int supplierQty) {
        ReconciliationDiscrepancy discrepancy = new ReconciliationDiscrepancy();
        discrepancy.setSku(local.getSku());
        discrepancy.setLocalQuantity(local.getQuantityOnHand());
        discrepancy.setSupplierQuantity(supplierQty);
        discrepancy.setDetectedAt(Instant.now());
        discrepancy.setResolved(false);
        discrepancyRepository.save(discrepancy);
        log.warn("Discrepancy detected for SKU {} - local={} supplier={}",
                local.getSku(), local.getQuantityOnHand(), supplierQty);
    }
}
