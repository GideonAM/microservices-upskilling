package com.redeemerlives.payments_service.service;

import com.redeemerlives.payments_service.PaymentsRepository;
import com.redeemerlives.payments_service.dto.PaymentDto;
import com.redeemerlives.payments_service.entity.Payments;
import com.redeemerlives.payments_service.kafka.PaymentProducer;
import com.redeemerlives.payments_service.kafka.PaymentConfirmation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentsService {

    private final PaymentsRepository paymentsRepository;
    private final PaymentProducer paymentProducer;

    public String createPayment(PaymentDto paymentDto) {

        Payments payment = Payments.builder()
                .paymentMethod(paymentDto.getPaymentMethod())
                .amount(paymentDto.getAmount())
                .orderId(paymentDto.getOrderId())
                .build();
        paymentsRepository.save(payment);

        PaymentConfirmation producerDto = new PaymentConfirmation(
                paymentDto.getPaymentMethod().toString(),
                paymentDto.getAmount(),
                paymentDto.getOrderId(),
                paymentDto.getCustomer().firstname(),
                paymentDto.getCustomer().lastname(),
                paymentDto.getCustomer().email()
        );
        paymentProducer.sendPaymentsToKafka(producerDto);

        return "Payment processed successfully";
    }
}
