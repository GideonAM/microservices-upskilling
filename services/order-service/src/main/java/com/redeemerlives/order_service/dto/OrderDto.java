package com.redeemerlives.order_service.dto;

import com.redeemerlives.order_service.entity.PaymentMethod;

import java.util.List;

public record OrderDto(
        String customerId,
        PaymentMethod paymentMethod,
        List<OrderItemDto> orderItems
) {
}
