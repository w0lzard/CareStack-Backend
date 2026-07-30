package com.ryuken.carestack.dto;

public record PaymentResult(boolean successful, String transactionReference, String failureReason) {
    public static PaymentResult success(String transactionReference) {
        return new PaymentResult(true, transactionReference, null);
    }

    public static PaymentResult failure(String reason) {
        return new PaymentResult(false, null, reason);
    }
}
