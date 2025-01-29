package com.redeemerlives.order_service.mapper;

import com.redeemerlives.order_service.dto.OrderDto;
import com.redeemerlives.order_service.entity.Orders;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderMapper {

    public OrderDto toOrderDto(Orders order) {
        return OrderDto.builder()
                .orderId(order.getId())
                .customerId(order.getCustomerId())
                .createdAt(order.getCreatedAt())
                .paymentMethod(order.getPaymentMethod())
                .build();
    }

    public Orders toOrder(OrderDto orderDto) {
        return Orders.builder()
                .customerId(orderDto.customerId())
                .paymentMethod(orderDto.paymentMethod())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
