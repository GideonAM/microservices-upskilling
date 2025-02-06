package com.redeemerlives.order_service.repository;

import com.redeemerlives.order_service.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItems, String> {
}
