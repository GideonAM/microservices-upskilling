package com.redeemerlives.order_service.service;

import com.redeemerlives.order_service.clients.CustomerServiceClient;
import com.redeemerlives.order_service.clients.ProductServiceClient;
import com.redeemerlives.order_service.dto.CustomerDto;
import com.redeemerlives.order_service.dto.OrderDto;
import com.redeemerlives.order_service.dto.ProductPurchaseResponse;
import com.redeemerlives.order_service.entity.OrderItems;
import com.redeemerlives.order_service.entity.Orders;
import com.redeemerlives.order_service.exception.OperationNotPermitted;
import com.redeemerlives.order_service.mapper.OrderItemMapper;
import com.redeemerlives.order_service.repository.OrderItemRepository;
import com.redeemerlives.order_service.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerServiceClient customerServiceClient;
    private final ProductServiceClient productServiceClient;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    public String placeOrder(OrderDto orderDto) {
        CustomerDto customer = customerServiceClient.findCustomerById(orderDto.customerId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid customer id"));
        List<ProductPurchaseResponse> purchaseResponse = productServiceClient.purchaseProducts(orderDto.orderItems())
                .orElseThrow(() -> new OperationNotPermitted("Failed to place order"));

        Orders order = Orders.builder()
                .customerId(orderDto.customerId())
                .paymentMethod(orderDto.paymentMethod())
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        List<OrderItems> orderItems = purchaseResponse.stream()
                .map(item -> orderItemMapper.toOrderItem(item, order))
                .toList();
        orderItemRepository.saveAll(orderItems);

        // then proceed with payments
        // send order confirmation email
        return "Order placed successfully";
    }
}
