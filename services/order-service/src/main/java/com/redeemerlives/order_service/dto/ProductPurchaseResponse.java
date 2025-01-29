package com.redeemerlives.order_service.dto;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        String productId,
        int productQuantity,
        BigDecimal productPrice,
        String productName
) {
}
