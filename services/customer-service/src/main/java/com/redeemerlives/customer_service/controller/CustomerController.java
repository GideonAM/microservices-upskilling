package com.redeemerlives.customer_service.controller;

import com.redeemerlives.customer_service.dto.AuthenticationResponse;
import com.redeemerlives.customer_service.dto.CustomerDto;
import com.redeemerlives.customer_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(201).body(customerService.register(customerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.login(customerDto));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok(customerService.findById(customerId));
    }

    @PutMapping("/{customer-id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("customer-id") String customerId ,@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.updateCustomer(customerId ,customerDto));
    }

}
