package com.ryuken.carestack.service;

import com.ryuken.carestack.dto.PaymentRequestDto;
import com.ryuken.carestack.dto.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto processPayment(PaymentRequestDto request);
    PaymentResponseDto getPaymentById(Long id);
}
