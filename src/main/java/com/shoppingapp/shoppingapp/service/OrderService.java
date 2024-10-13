package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.models.Orders;

import java.util.List;

public interface OrderService {
    Orders addOrder(Orders order);
    Orders updateOrder(Orders order);
    List<Orders> getAllOrders();
    Orders getOrderById(Long id);
    void deleteOrder(Orders order);
    void deleteOrders(List<Orders> orders);
    int getTotalOrders();
}
