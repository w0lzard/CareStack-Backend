package com.ryuken.carestack.publisher;

import com.ryuken.carestack.config.KafkaTopicConfig;
import com.ryuken.carestack.event.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishPaymentCompleted(PaymentCompletedEvent event) {
        kafkaTemplate.send(KafkaTopicConfig.PAYMENT_COMPLETED_TOPIC, event.paymentId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish payment.completed event for payment {}", event.paymentId(), ex);
                    } else {
                        log.info("Published payment.completed event for payment {}", event.paymentId());
                    }
                });
    }
}
