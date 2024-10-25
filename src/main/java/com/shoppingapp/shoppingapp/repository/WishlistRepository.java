package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    List<Wishlist> findWishListItemByUserId(long userId);
}
