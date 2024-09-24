package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductName(String name);
}
