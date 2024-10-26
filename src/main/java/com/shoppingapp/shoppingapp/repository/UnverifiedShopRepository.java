package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.UnverifiedShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnverifiedShopRepository extends JpaRepository<UnverifiedShop, Long> {
    boolean existsByShopName(String unverifiedShopName);

    Optional<UnverifiedShop> findByUserId(Long userId);
}
