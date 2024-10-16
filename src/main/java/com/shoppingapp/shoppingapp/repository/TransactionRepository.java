package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByShop_ShopId(Long shopId);
}
