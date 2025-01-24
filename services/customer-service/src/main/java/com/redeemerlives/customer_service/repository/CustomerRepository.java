package com.redeemerlives.customer_service.repository;

import com.redeemerlives.customer_service.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customers, String> {
    Optional<Customers> findByEmail(String username);
}
