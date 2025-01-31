package com.redeemerlives.order_service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PaymentDto {
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private String orderId;
    private CustomerDto customer;
}
