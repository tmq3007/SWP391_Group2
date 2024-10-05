package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.Cart;
import com.shoppingapp.shoppingapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
   // public Cart findByUserIdAndProductId(Long userId, Long productId);
   @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.product.productId = :productId")
   Cart findCartByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
    public List<Cart> findByUserId(Long userId);


}
