package com.redeemerlives.order_service.kafka;

import java.time.LocalDateTime;

public record OrderConfirmation(
        String orderId,
        LocalDateTime createdAt,
        String firstname,
        String lastname,
        String email
) {
}
