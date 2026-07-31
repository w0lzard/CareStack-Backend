package com.ryuken.carestack.service.impl;

import com.ryuken.carestack.dto.PaymentResult;
import com.ryuken.carestack.entity.PaymentMethod;
import com.ryuken.carestack.service.PaymentStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class InsurancePaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentMethod supportedMethod() {
        return PaymentMethod.INSURANCE;
    }

    @Override
    public PaymentResult charge(java.math.BigDecimal amount, String payerReference) {
        // In a real system this would call an insurance provider's API.
        log.info("Filing insurance claim for {} - policy ref {}", amount, payerReference);
        return PaymentResult.success("CLAIM-" + UUID.randomUUID());
    }
}
