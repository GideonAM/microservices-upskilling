package com.redeemerlives.order_service.clients;

import com.redeemerlives.order_service.dto.OrderItemDto;
import com.redeemerlives.order_service.dto.ProductPurchaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "products-service")
public interface ProductServiceClient {
    @PostMapping("/api/v1/products/purchase")
    Optional<List<ProductPurchaseResponse>> purchaseProducts(@RequestBody List<OrderItemDto> orderItems);
}
