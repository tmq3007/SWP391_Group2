package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {
}
