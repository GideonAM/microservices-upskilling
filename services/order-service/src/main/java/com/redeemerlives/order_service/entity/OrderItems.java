package com.redeemerlives.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false, updatable = false)
    private String productId;
    @Column(nullable = false)
    private int productQuantity;
    @Column(nullable = false)
    private BigDecimal productPrice;
    @Column(nullable = false)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;
}
