package com.redeemerlives.products_service.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDto(
        String id,
        String name,
        String description,
        Integer availableQuantity,
        BigDecimal price,
        String categoryId
) {
}
