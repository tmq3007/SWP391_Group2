package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT COUNT(*) FROM orders WHERE user_id = :userId", nativeQuery = true)
    int countOrderByUserId(@Param("userId") Long userId);
}
