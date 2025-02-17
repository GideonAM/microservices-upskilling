package com.redeemerlives.notification_service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EmailTemplates {
    PAYMENT_EMAIL("payment-email.html", "Payment Notification"),
    ORDER_EMAIL("order-email.html", "Order Notification")
    ;

    private final String templateName;
    private final String emailTopic;
}
