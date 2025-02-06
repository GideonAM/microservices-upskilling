package com.redeemerlives.customer_service.repository;

import com.redeemerlives.customer_service.entity.Customers;
import com.redeemerlives.customer_service.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByToken(String token);
    Optional<Token> findByCustomers(Customers customers);
}
