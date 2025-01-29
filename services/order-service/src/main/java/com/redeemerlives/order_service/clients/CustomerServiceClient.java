package com.redeemerlives.order_service.clients;

import com.redeemerlives.order_service.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "customer-service")
public interface CustomerServiceClient {
    @GetMapping("/api/v1/customers/{customer-id}")
    Optional<CustomerDto> findCustomerById(@PathVariable("customer-id") String customerId);
}
