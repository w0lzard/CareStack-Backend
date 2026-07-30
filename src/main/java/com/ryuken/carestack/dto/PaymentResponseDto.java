package com.ryuken.carestack.dto;

import com.ryuken.carestack.entity.Payment;
import com.ryuken.carestack.entity.PaymentMethod;
import com.ryuken.carestack.entity.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResponseDto(
        Long id,
        Long patientId,
        BigDecimal amount,
        PaymentMethod method,
        PaymentStatus status,
        String transactionReference,
        Instant createdAt
) {
    public static PaymentResponseDto fromEntity(Payment p) {
        return new PaymentResponseDto(p.getId(), p.getPatientId(), p.getAmount(),
                p.getMethod(), p.getStatus(), p.getTransactionReference(), p.getCreatedAt());
    }
}

