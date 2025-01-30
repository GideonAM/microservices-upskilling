package com.redeemerlives.order_service.mapper;

import com.redeemerlives.order_service.dto.CustomerDto;
import com.redeemerlives.order_service.dto.OrderDto;
import com.redeemerlives.order_service.dto.PaymentDto;
import com.redeemerlives.order_service.dto.PaymentMethod;
import com.redeemerlives.order_service.entity.Orders;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    public PaymentDto toPaymentDto(Orders savedOrder,
                                   CustomerDto customer,
                                   BigDecimal totalOrderCost,
                                   PaymentMethod paymentMethod) {

        return PaymentDto.builder()
                .paymentMethod(paymentMethod)
                .amount(totalOrderCost)
                .orderId(savedOrder.getId())
                .customer(customer)
                .build();
    }
}
