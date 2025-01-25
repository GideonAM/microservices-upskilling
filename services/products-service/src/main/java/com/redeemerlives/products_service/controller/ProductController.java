package com.redeemerlives.products_service.controller;

import com.redeemerlives.products_service.dto.PageResponse;
import com.redeemerlives.products_service.dto.ProductDto;
import com.redeemerlives.products_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductDto>> getAllProducts(
            @RequestParam(name = "page", value = "0", required = false) int page,
            @RequestParam(name = "size", value = "10", required = false) int size
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @GetMapping("/{product-name}")
    public ResponseEntity<PageResponse<ProductDto>> searchProductByName(
            @PathVariable("product-name") String productName,
            @RequestParam(name = "page", value = "0", required = false) int page,
            @RequestParam(name = "size", value = "10", required = false) int size
    ) {
        return ResponseEntity.ok(productService.searchProductByName(productName, page, size));
    }

    @PutMapping("/{product-id}")
    public ResponseEntity<String> updateProduct(@PathVariable("product-id") String productId,
                                                @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProduct(productId, productDto));
    }

    @DeleteMapping("/{product-id}")
    public ResponseEntity<String> deleteProductById(@PathVariable("product-id") String productId) {
        return ResponseEntity.ok(productService.deleteProductById(productId));
    }

}
