package com.ryuken.carestack.service.impl;

import com.ryuken.carestack.dto.SupplierStockLevel;
import com.ryuken.carestack.service.SupplierStockClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MockSupplierStockClient implements SupplierStockClient {

    @Override
    public List<SupplierStockLevel> fetchSupplierStock() {
        log.debug("Fetching stock levels from supplier feed (mock)");
        return List.of();
    }
}
