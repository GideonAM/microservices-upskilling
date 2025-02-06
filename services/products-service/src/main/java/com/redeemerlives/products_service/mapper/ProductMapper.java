package com.redeemerlives.products_service.mapper;

import com.redeemerlives.products_service.dto.ProductDto;
import com.redeemerlives.products_service.dto.ProductPurchaseResponse;
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
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .availableQuantity(product.getAvailableQuantity())
                .categoryId(product.getCategory().getId())
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

    public ProductPurchaseResponse toPurchasedProduct(Products product, int quantityPurchased) {
        return ProductPurchaseResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productQuantity(quantityPurchased)
                .productPrice(product.getPrice())
                .build();
    }
}
