package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.Cart;
import com.shoppingapp.shoppingapp.models.CartItem;
import com.shoppingapp.shoppingapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndProductAndBuyUnit(Cart cart, Product product, String unitBuy);
}
