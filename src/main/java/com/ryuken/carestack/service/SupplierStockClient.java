package com.ryuken.carestack.service;

import com.ryuken.carestack.dto.SupplierStockLevel;

import java.util.List;

public interface SupplierStockClient {
    List<SupplierStockLevel> fetchSupplierStock();
}
