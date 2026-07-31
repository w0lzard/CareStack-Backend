package com.ryuken.carestack.service.impl;

import com.ryuken.carestack.service.NotificationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SmsNotificationSender implements NotificationSender {

    @Override
    public void send(String recipient, String message) {
        // In a real system this would call an SMS gateway (Twilio, MSG91...).
        log.info("Sending SMS to {}: {}", recipient, message);
    }

    @Override
    public boolean supports(String recipient) {
        return recipient != null && recipient.matches("\\+?[0-9]{7,15}");
    }
}
