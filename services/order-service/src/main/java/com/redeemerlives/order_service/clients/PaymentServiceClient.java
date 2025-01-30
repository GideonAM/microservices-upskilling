package com.redeemerlives.order_service.clients;

import com.redeemerlives.order_service.dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payments-service")
public interface PaymentServiceClient {

    @PostMapping("/api/v1/payments")
    String createPayment(@RequestBody PaymentDto paymentDto);

}
