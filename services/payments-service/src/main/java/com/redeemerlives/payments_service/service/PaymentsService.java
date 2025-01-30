package com.redeemerlives.payments_service.service;

import com.redeemerlives.payments_service.PaymentsRepository;
import com.redeemerlives.payments_service.dto.PaymentDto;
import com.redeemerlives.payments_service.entity.Payments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentsService {

    private final PaymentsRepository paymentsRepository;

    public String createPayment(PaymentDto paymentDto) {

        Payments payment = Payments.builder()
                .paymentMethod(paymentDto.getPaymentMethod())
                .amount(paymentDto.getAmount())
                .orderId(paymentDto.getOrderId())
                .build();
        paymentsRepository.save(payment);

        // Todo
        // send payment confirmation to Kafka broker for processing

        return "Payment processed successfully";
    }
}
