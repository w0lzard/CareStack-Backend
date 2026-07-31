package com.ryuken.carestack.service;

import com.ryuken.carestack.dto.PaymentResult;
import com.ryuken.carestack.entity.PaymentMethod;

import java.math.BigDecimal;

public interface PaymentStrategy {
    PaymentMethod supportedMethod();
    PaymentResult charge(BigDecimal amount, String payerReference);
}
