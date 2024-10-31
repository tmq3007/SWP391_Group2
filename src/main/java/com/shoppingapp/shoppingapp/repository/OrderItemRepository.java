package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {

    @Query(value = "SELECT COUNT(*) FROM order_items WHERE shop_shop_id = :shopId", nativeQuery = true)
    int countOrderItemsByShopId(@Param("shopId") Long shopId);
}
