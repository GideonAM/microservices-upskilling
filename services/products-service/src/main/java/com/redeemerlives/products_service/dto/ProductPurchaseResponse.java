package com.redeemerlives.products_service.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductPurchaseResponse(
        String productId,
        int productQuantity,
        BigDecimal productPrice,
        String productName
) {
}
