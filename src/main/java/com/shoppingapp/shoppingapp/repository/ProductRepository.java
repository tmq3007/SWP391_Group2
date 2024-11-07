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

    @Query(value = "SELECT * FROM product where average_rating is not null and average_rating != 0 ORDER BY average_rating DESC LIMIT 10", nativeQuery = true)
    List<Product> findTop10ByHighestAverageRating();

    @Query(value = "SELECT p.* FROM product p JOIN order_items oi ON oi.product_name = p.product_name GROUP BY p.product_id ORDER BY COUNT(oi.order_items_id) DESC LIMIT 10", nativeQuery = true)
    List<Product> findTop10ByMostSold();


    List<Product> findProductsByProductName(String productName);

}
