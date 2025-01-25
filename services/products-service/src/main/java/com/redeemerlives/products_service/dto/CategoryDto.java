package com.redeemerlives.products_service.dto;

import lombok.Builder;

@Builder
public record CategoryDto(
        String id,
        String name,
        String description
) {
}
