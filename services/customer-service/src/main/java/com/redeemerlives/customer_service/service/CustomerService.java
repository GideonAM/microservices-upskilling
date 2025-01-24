package com.redeemerlives.customer_service.service;

import com.redeemerlives.customer_service.dto.AuthenticationResponse;
import com.redeemerlives.customer_service.dto.CustomerDto;
import com.redeemerlives.customer_service.dto.CustomerDtoResponse;
import com.redeemerlives.customer_service.entity.Customers;
import com.redeemerlives.customer_service.entity.Token;
import com.redeemerlives.customer_service.repository.CustomerRepository;
import com.redeemerlives.customer_service.repository.TokenRepository;
import com.redeemerlives.customer_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse register(CustomerDto customerDto) {
        Customers customer = Customers.builder()
                .email(customerDto.getEmail())
                .firstname(customerDto.getFirstname())
                .lastname(customerDto.getLastname())
                .password(passwordEncoder.encode(customerDto.getPassword()))
                .build();
        Customers savedCustomer = customerRepository.save(customer);

        var jwt = jwtService.generateToken(customerDto.getEmail());
        Token token = Token.builder()
                .customers(savedCustomer)
                .token(jwt)
                .build();
        tokenRepository.save(token);

        CustomerDtoResponse customerDtoResponse = CustomerDtoResponse.builder()
                .firstname(customerDto.getFirstname())
                .lastname(customerDto.getLastname())
                .email(customerDto.getEmail())
                .build();

        return AuthenticationResponse.builder()
                .message("Registration successful")
                .token(jwt)
                .user(customerDtoResponse)
                .build();
    }

    public AuthenticationResponse login(CustomerDto customerDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(customerDto.getEmail(), customerDto.getPassword());

        Customers customer = (Customers) authenticationToken.getDetails();
        tokenRepository.findByCustomers(customer)
                .ifPresent(tokenRepository::delete);

        var jwt = jwtService.generateToken(customerDto.getEmail());
        Token token = Token.builder()
                .customers(customer)
                .token(jwt)
                .build();
        tokenRepository.save(token);

        CustomerDtoResponse customerDtoResponse = CustomerDtoResponse.builder()
                .firstname(customerDto.getFirstname())
                .lastname(customerDto.getLastname())
                .email(customerDto.getEmail())
                .build();

        return AuthenticationResponse.builder()
                .message("Login successful")
                .token(jwt)
                .user(customerDtoResponse)
                .build();
    }
}
