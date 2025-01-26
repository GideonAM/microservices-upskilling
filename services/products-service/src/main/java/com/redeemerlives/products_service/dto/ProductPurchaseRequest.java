package com.redeemerlives.products_service.dto;

public record ProductPurchaseRequest(
        String productId,
        int productQuantity
) {
}
