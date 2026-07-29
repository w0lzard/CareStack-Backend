package com.ryuken.carestack.event;

import com.ryuken.carestack.entity.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentCompletedEvent(
        Long paymentId,
        Long patientId,
        BigDecimal amount,
        PaymentMethod method,
        String transactionReference,
        Instant completedAt
        ) {
}
