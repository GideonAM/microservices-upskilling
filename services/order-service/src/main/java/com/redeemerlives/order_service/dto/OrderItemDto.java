package com.redeemerlives.order_service.dto;

public record OrderItemDto(
        String productId,
        int productQuantity
) {
}
