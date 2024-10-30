package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.models.OrderItems;

import java.util.List;

public interface OrderItemsService {
    List<OrderItems> getAll();
    List<OrderItems> getByOrderId(Long id);
    String add(OrderItems orderItems);
    List<OrderItems> getAllByShopId(Long orderId);
    OrderItems getById(Long id);
}
