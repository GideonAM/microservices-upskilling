package com.redeemerlives.notification_service.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.redeemerlives.notification_service.EmailTemplates.ORDER_EMAIL;
import static com.redeemerlives.notification_service.EmailTemplates.PAYMENT_EMAIL;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentEmail(
            String receiverEmail,
            BigDecimal paymentAmount,
            String paymentMethod,
            String receiverName,
            String orderId
    ) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.displayName());

        helper.setFrom("no-reply@ms-upskilling.com");
        helper.setTo(receiverEmail);
        helper.setSubject(PAYMENT_EMAIL.getEmailTopic());

        Map<String, Object> variables = new HashMap<>();
        variables.put("paymentMethod", paymentMethod);
        variables.put("receiverName", receiverName);
        variables.put("orderId", orderId);
        variables.put("paymentAmount", paymentAmount);

        Context context = new Context();
        context.setVariables(variables);

        try {
            String html = templateEngine.process(PAYMENT_EMAIL.getTemplateName(), context);
            helper.setText(html, true);
            javaMailSender.send(message);
        } catch (MessagingException exception) {
            log.warn("FAILED TO SEND EMAIL TO {}", receiverEmail);
            throw new MessagingException("Failed to send Payment Confirmation email");
        }
    }

    public void sendOrderEmail(String email,
                               String orderId,
                               LocalDateTime createdAt,
                               String customerName) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.displayName());

        helper.setFrom("no-reply@ms-upskilling.com");
        helper.setTo(email);
        helper.setSubject(ORDER_EMAIL.getEmailTopic());

        Map<String, Object> variables = new HashMap<>();
        variables.put("orderId", orderId);
        variables.put("createdAt", createdAt);
        variables.put("customerName", customerName);

        Context  context = new Context();
        context.setVariables(variables);

        try {
            String html = templateEngine.process(ORDER_EMAIL.getTemplateName(), context);
            helper.setText(html, true);
            javaMailSender.send(message);
        } catch (MessagingException exception) {
            log.warn("FAILED TO SEND EMAIL TO {}", email);
            throw new MessagingException("Failed to send Order Confirmation email");
        }
    }
}
