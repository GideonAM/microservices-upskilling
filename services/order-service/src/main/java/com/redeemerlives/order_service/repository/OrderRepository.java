package com.redeemerlives.order_service.repository;

import com.redeemerlives.order_service.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, String> {
    Page<Orders> findAllByCustomerId(String customerId , Pageable pageable);
}
