package com.ryuken.carestack.listener;

import com.ryuken.carestack.config.KafkaTopicConfig;
import com.ryuken.carestack.event.PaymentCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class PaymentEventListener {

    private final Set<String> processedTransactionReferences = ConcurrentHashMap.newKeySet();

    @KafkaListener(topics = KafkaTopicConfig.PAYMENT_COMPLETED_TOPIC, groupId = "medical-records-audit")
    public void handle(PaymentCompletedEvent event, Acknowledgment acknowledgment) {
        if (!processedTransactionReferences.add(event.transactionReference())) {
            log.info("Duplicate delivery of payment event {} - skipping re-processing",
                    event.transactionReference());
            acknowledgment.acknowledge();
            return;
        }

        log.info("Processing payment.completed event for payment {} (patient {}, amount {})",
                event.paymentId(), event.patientId(), event.amount());

        // In a fuller implementation this might append a billing note to the patient's
        // record, or notify a billing-audit dashboard - kept minimal here to focus on
        // the Kafka wiring itself.

        acknowledgment.acknowledge();
    }
}
