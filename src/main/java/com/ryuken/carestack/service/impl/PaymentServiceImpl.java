package com.ryuken.carestack.service.impl;

import com.ryuken.carestack.dto.PaymentRequestDto;
import com.ryuken.carestack.dto.PaymentResponseDto;
import com.ryuken.carestack.dto.PaymentResult;
import com.ryuken.carestack.entity.Payment;
import com.ryuken.carestack.entity.PaymentMethod;
import com.ryuken.carestack.entity.PaymentStatus;
import com.ryuken.carestack.event.PaymentCompletedEvent;
import com.ryuken.carestack.publisher.PaymentEventPublisher;
import com.ryuken.carestack.repository.PaymentRepository;
import com.ryuken.carestack.service.PaymentService;
import com.ryuken.carestack.service.PaymentStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher paymentEventPublisher;
    private final Map<PaymentMethod, PaymentStrategy> strategiesByMethod;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              PaymentEventPublisher paymentEventPublisher,
                              List<PaymentStrategy> strategies) {
        this.paymentRepository = paymentRepository;
        this.paymentEventPublisher = paymentEventPublisher;
        this.strategiesByMethod = new EnumMap<>(PaymentMethod.class);
        for (PaymentStrategy strategy : strategies) {
            this.strategiesByMethod.put(strategy.supportedMethod(), strategy);
        }
    }

    @Override
    @Transactional
    public PaymentResponseDto processPayment(PaymentRequestDto request) {
        PaymentStrategy strategy = strategiesByMethod.get(request.method());
        if (strategy == null) {
            throw new IllegalStateException("No PaymentStrategy registered for method " + request.method());
        }

        PaymentResult result = strategy.charge(request.amount(), request.payerReference());

        Payment payment = new Payment();
        payment.setPatientId(request.patientId());
        payment.setAmount(request.amount());
        payment.setMethod(request.method());
        payment.setStatus(result.successful() ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
        payment.setTransactionReference(result.transactionReference());
        payment.setCreatedAt(Instant.now());
        Payment saved = paymentRepository.save(payment);

        log.info("Payment {} for patient {} via {} - status {}",
                saved.getId(), saved.getPatientId(), saved.getMethod(), saved.getStatus());

        if (result.successful()) {
            paymentEventPublisher.publishPaymentCompleted(new PaymentCompletedEvent(
                    saved.getId(), saved.getPatientId(), saved.getAmount(),
                    saved.getMethod(), saved.getTransactionReference(), saved.getCreatedAt()));
        }

        return PaymentResponseDto.fromEntity(saved);
    }

    @Override
    public PaymentResponseDto getPaymentById(Long id) {
        return PaymentResponseDto.fromEntity(paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found: " + id)));
    }
}
