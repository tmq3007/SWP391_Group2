package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductName(String name);

    @Query(value = "SELECT * FROM product p WHERE p.shop_id = :shopId", nativeQuery = true)
    List<Product> findAllProductsByShopId(@Param("shopId") Long shopId);

    @Query(value = "SELECT COUNT(*) FROM product WHERE shop_id = :shopId", nativeQuery = true)
    int countProductsByShopId(@Param("shopId") Long shopId);
}
