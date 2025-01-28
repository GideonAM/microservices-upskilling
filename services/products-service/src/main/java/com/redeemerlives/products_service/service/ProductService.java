package com.redeemerlives.products_service.service;

import com.redeemerlives.products_service.dto.PageResponse;
import com.redeemerlives.products_service.dto.ProductDto;
import com.redeemerlives.products_service.dto.ProductPurchaseRequest;
import com.redeemerlives.products_service.dto.ProductPurchaseResponse;
import com.redeemerlives.products_service.entity.Category;
import com.redeemerlives.products_service.entity.Products;
import com.redeemerlives.products_service.exception.OperationNotPermittedException;
import com.redeemerlives.products_service.mapper.ProductMapper;
import com.redeemerlives.products_service.repository.CategoryRepository;
import com.redeemerlives.products_service.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public String createProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Product category not found"));

        Products product = productMapper.toProduct(productDto, category);
        productRepository.save(product);
        return "Product created successfully";
    }

    public PageResponse<ProductDto> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Products> products = productRepository.findAll(pageable);
        List<ProductDto> data = products.stream().map(productMapper::toProductDto).toList();

        return new PageResponse<>(
                data,
                products.getTotalElements(),
                products.getTotalPages(),
                products.isLast(),
                products.isFirst()
        );
    }

    public PageResponse<ProductDto> searchProductByName(String productName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Products> products = productRepository.findAllByNameContainingIgnoreCase(productName, pageable);
        List<ProductDto> data = products.stream().map(productMapper::toProductDto).toList();

        return new PageResponse<>(
                data,
                products.getTotalElements(),
                products.getTotalPages(),
                products.isLast(),
                products.isFirst()
        );
    }

    public String updateProduct(String productId, ProductDto productDto) {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Category category = categoryRepository.findById(productDto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Product category not found"));

        Products updatedProduct = productMapper.toUpdatedProduct(product, category, productDto);
        productRepository.save(updatedProduct);
        return "Product updated successfully";
    }

    public String deleteProductById(String productId) {
        productRepository.findById(productId)
                .ifPresentOrElse(productRepository::delete, () -> {
                    throw new EntityNotFoundException("Product not found");
                });

        return "Product deleted successfully";
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> purchaseRequests) {
        List<String> productIds = purchaseRequests.stream().map(ProductPurchaseRequest::productId).toList();
        List<Products> products = productRepository.findAllById(productIds);

        if (productIds.size() != products.size())
            throw new OperationNotPermittedException("Some of the purchased products does not exist");

        purchaseRequests.sort(Comparator.comparing(ProductPurchaseRequest::productId));
        products.sort(Comparator.comparing(Products::getId));

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < products.size(); i++) {
            var product = products.get(i);
            var orderItem = purchaseRequests.get(i);

            if (product.getAvailableQuantity() < orderItem.productQuantity())
                throw new OperationNotPermittedException(String.format("%s is currently out of stock", product.getName()));

            int newAvailableQuantity = product.getAvailableQuantity() - orderItem.productQuantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);

            int quantityPurchased = orderItem.productQuantity();
            purchasedProducts.add(productMapper.toPurchasedProduct(product, quantityPurchased));
        }

        return purchasedProducts;
    }
}
