package com.redeemerlives.order_service.kafka;

import java.time.LocalDateTime;

public record OrderProducerDto(
        String orderId,
        LocalDateTime createdAt,
        String firstname,
        String lastname,
        String email
) {
}
