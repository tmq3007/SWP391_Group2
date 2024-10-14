package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    boolean existsByShopName(String shopName);

        // Tìm Shop dựa trên user
        Optional<Shop> findByUserId(Long userId);


}
