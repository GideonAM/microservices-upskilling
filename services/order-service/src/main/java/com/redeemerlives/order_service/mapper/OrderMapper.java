package com.redeemerlives.order_service.mapper;

import com.redeemerlives.order_service.dto.OrderDto;
import com.redeemerlives.order_service.entity.Orders;
import org.springframework.stereotype.Component;

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

}
