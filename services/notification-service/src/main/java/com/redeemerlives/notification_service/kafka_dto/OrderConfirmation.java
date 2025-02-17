package com.redeemerlives.notification_service.kafka_dto;

import java.time.LocalDateTime;

public record OrderConfirmation(
        String orderId,
        LocalDateTime createdAt,
        String firstname,
        String lastname,
        String email
) {
}
