package com.ryuken.carestack.service.impl;

import com.ryuken.carestack.dto.PaymentResult;
import com.ryuken.carestack.entity.PaymentMethod;
import com.ryuken.carestack.service.PaymentStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@Slf4j
public class UpiPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentMethod supportedMethod() {
        return PaymentMethod.UPI;
    }

    @Override
    public PaymentResult charge(BigDecimal amount, String payerReference) {
        log.info("Charging {} via UPI for payer {}", amount, payerReference);
        return PaymentResult.success("UPI-" + UUID.randomUUID());
    }
}
