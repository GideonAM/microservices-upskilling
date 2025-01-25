package com.redeemerlives.customer_service.mapper;

import com.redeemerlives.customer_service.dto.CustomerDto;
import com.redeemerlives.customer_service.entity.Customers;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

    private final PasswordEncoder passwordEncoder;

    public CustomerDto toCustomerDto(Customers customer) {
        return CustomerDto.builder()
                .email(customer.getEmail())
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .build();
    }

    public Customers toCustomer(CustomerDto customerDto) {
        return Customers.builder()
                .email(customerDto.getEmail())
                .firstname(customerDto.getFirstname())
                .lastname(customerDto.getLastname())
                .password(passwordEncoder.encode(customerDto.getPassword()))
                .build();
    }
}
