package com.redeemerlives.notification_service.kafka_dto;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String paymentMethod,
        BigDecimal amount,
        String orderId,
        String firstname,
        String lastname,
        String email
) {
}
