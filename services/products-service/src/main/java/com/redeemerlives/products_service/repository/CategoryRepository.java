package com.redeemerlives.products_service.repository;

import com.redeemerlives.products_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findAllByNameContaining(String categoryName);
}
