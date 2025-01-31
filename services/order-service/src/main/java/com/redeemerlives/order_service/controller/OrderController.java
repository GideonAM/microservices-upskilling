package com.redeemerlives.order_service.controller;

import com.redeemerlives.order_service.dto.OrderDto;
import com.redeemerlives.order_service.dto.PageResponse;
import com.redeemerlives.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(orderDto));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<PageResponse<OrderDto>> getAllOrders(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @PathVariable("customer-id") String customerId
    ) {
        return ResponseEntity.ok(orderService.getAllOrders(page, size, customerId));
    }

}
