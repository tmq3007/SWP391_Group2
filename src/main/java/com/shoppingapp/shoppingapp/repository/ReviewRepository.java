package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "SELECT * FROM review p WHERE p.product_id = :productId", nativeQuery = true)
    List<Review> findAllReviewsByProductId(@Param("productId") Long productId);


}
