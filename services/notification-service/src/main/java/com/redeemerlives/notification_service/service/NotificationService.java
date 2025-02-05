package com.redeemerlives.notification_service.service;

import com.redeemerlives.notification_service.kafka_dto.PaymentConfirmation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void sendPaymentEmail(PaymentConfirmation paymentConfirmation) throws MessagingException {
        var customerName = String.format("%s %s", paymentConfirmation.firstname(), paymentConfirmation.lastname());
        emailService.sendPaymentEmail(
                paymentConfirmation.email(),
                paymentConfirmation.amount(),
                paymentConfirmation.paymentMethod(),
                customerName,
                paymentConfirmation.orderId()
        );
    }

}
