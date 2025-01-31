package com.redeemerlives.products_service.repository;

import com.redeemerlives.products_service.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, String> {
    Page<Products> findAllByNameContainingIgnoreCase(String productName, Pageable pageable);
}
