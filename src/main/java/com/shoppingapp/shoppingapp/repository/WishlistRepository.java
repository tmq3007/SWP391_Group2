package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<WishList,Long> {

}
