package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductName(String name);

    default List<Product> findByShopId(Long shopId) {
        return null;
    }
}
