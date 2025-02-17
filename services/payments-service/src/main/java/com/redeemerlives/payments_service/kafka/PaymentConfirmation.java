package com.redeemerlives.payments_service.kafka;

import java.math.BigDecimal;

public record PaymentConfirmation(
    String paymentMethod,
    BigDecimal amount,
    String orderId,
    String firstname,
    String lastname,
    String email) {
}
