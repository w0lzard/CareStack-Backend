package com.ryuken.carestack.service.impl;

import com.ryuken.carestack.service.NotificationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailNotificationSender implements NotificationSender {

    @Override
    public void send(String recipient, String message) {
        // In a real system this would call a mail provider (SMTP, SES, SendGrid...).
        log.info("Sending EMAIL to {}: {}", recipient, message);
    }

    @Override
    public boolean supports(String recipient) {
        return recipient != null && recipient.contains("@");
    }
}
