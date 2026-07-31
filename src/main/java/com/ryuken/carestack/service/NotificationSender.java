package com.ryuken.carestack.service;

public interface NotificationSender {
    void send(String recipient, String message);

    /** Lets the caller pick a sender for a given contact without knowing concrete types. */
    boolean supports(String recipient);
}
