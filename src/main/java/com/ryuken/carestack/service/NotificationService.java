package com.ryuken.carestack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final List<NotificationSender> senders;

    public void notify(String recipient, String message) {
        senders.stream()
                .filter(sender -> sender.supports(recipient))
                .findFirst()
                .ifPresentOrElse(
                        sender -> sender.send(recipient, message),
                        () -> log.warn("No notification sender supports recipient: {}", recipient)
                );
    }
}

