package com.redeemerlives.products_service.mapper;

import com.redeemerlives.products_service.dto.ProductDto;
import com.redeemerlives.products_service.entity.Category;
import com.redeemerlives.products_service.entity.Products;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Products toProduct(ProductDto productDto, Category category) {
        return Products.builder()
                .name(productDto.name())
                .description(productDto.description())
                .availableQuantity(productDto.availableQuantity())
                .price(productDto.price())
                .category(category)
                .build();
    }

    public ProductDto toProductDto(Products product) {
        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .availableQuantity(product.getAvailableQuantity())
                .build();
    }

    public Products toUpdatedProduct(Products product, Category category, ProductDto productDto) {
        return Products.builder()
                .id(product.getId())
                .category(category)
                .name(productDto.name())
                .description(productDto.description())
                .availableQuantity(productDto.availableQuantity())
                .price(productDto.price())
                .build();
    }
}
