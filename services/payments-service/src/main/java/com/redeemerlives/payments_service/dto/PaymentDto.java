package com.redeemerlives.payments_service.dto;

import com.redeemerlives.payments_service.utils.CustomerDto;
import com.redeemerlives.payments_service.utils.PaymentMethod;
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
