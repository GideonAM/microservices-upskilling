package com.redeemerlives.order_service.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDto(
        String orderId,
        LocalDateTime createdAt,
        String customerId,
        PaymentMethod paymentMethod,
        List<OrderItemDto> orderItems
) {
}
