package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT COUNT(*) FROM orders WHERE user_id = :userId", nativeQuery = true)
    int countOrderByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT YEAR(order_date) AS orderYear, MONTH(order_date) AS orderMonth, COUNT(*) AS orderCount " +
            "FROM orders " +
            "GROUP BY YEAR(order_date), MONTH(order_date) " +
            "ORDER BY orderYear, orderMonth", nativeQuery = true)
    List<Object[]> countOrdersByMonthAndYear();

    @Query(value = "SELECT SUM(final_total) FROM orders", nativeQuery = true)
    Double sumTotalRevenue();

}
