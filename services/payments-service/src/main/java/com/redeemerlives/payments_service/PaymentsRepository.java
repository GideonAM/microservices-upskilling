package com.redeemerlives.payments_service;

import com.redeemerlives.payments_service.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payments, String> {
}
