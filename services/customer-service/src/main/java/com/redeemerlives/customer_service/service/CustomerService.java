package com.redeemerlives.customer_service.service;

import com.redeemerlives.customer_service.dto.AuthenticationResponse;
import com.redeemerlives.customer_service.dto.CustomerDto;
import com.redeemerlives.customer_service.entity.Customers;
import com.redeemerlives.customer_service.entity.Token;
import com.redeemerlives.customer_service.mapper.CustomerMapper;
import com.redeemerlives.customer_service.repository.CustomerRepository;
import com.redeemerlives.customer_service.repository.TokenRepository;
import com.redeemerlives.customer_service.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;
    private final CustomerMapper customerMapper;

    public AuthenticationResponse register(CustomerDto customerDto) {
        Customers customer = customerMapper.toCustomer(customerDto);
        Customers savedCustomer = customerRepository.save(customer);

        var jwt = jwtService.generateToken(customerDto.getEmail());
        Token token = Token.builder()
                .customers(savedCustomer)
                .token(jwt)
                .build();

        tokenRepository.save(token);
        CustomerDto customerDtoResponse = customerMapper.toCustomerDto(customer);

        return AuthenticationResponse.builder()
                .message("Registration successful")
                .token(jwt)
                .user(customerDtoResponse)
                .build();
    }

    public AuthenticationResponse login(CustomerDto customerDto) {
        Authentication authenticated = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(customerDto.getEmail(), customerDto.getPassword())
        );

        Customers customer = (Customers) authenticated.getDetails();
        tokenRepository.findByCustomers(customer)
                .ifPresent(tokenRepository::delete);

        var jwt = jwtService.generateToken(customerDto.getEmail());
        Token token = Token.builder()
                .customers(customer)
                .token(jwt)
                .build();

        tokenRepository.save(token);
        CustomerDto customerDtoResponse = customerMapper.toCustomerDto(customer);

        return AuthenticationResponse.builder()
                .message("Login successful")
                .token(jwt)
                .user(customerDtoResponse)
                .build();
    }

    public CustomerDto findById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::toCustomerDto)
                .orElseThrow(() -> new EntityNotFoundException("No customer found"));
    }

    public CustomerDto updateCustomer(String customerId ,CustomerDto customerDto) {
        Customers customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("No customer found"));

        if (StringUtils.isNotBlank(customerDto.getEmail()))
            customer.setEmail(customerDto.getEmail());

        if (StringUtils.isNotBlank(customerDto.getFirstname()))
            customer.setFirstname(customerDto.getFirstname());

        if (StringUtils.isNotBlank(customerDto.getLastname()))
            customer.setLastname(customerDto.getLastname());

        customerRepository.save(customer);
        return customerMapper.toCustomerDto(customer);
    }
}
