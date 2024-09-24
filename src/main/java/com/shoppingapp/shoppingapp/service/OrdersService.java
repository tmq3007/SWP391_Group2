package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.OrdersCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.OrdersUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.OrdersResponse;
import com.shoppingapp.shoppingapp.models.Orders;

import java.util.List;

public interface OrdersService {
    OrdersResponse addOrder(OrdersCreationRequest order);
    List<OrdersResponse> getAllOrders();
    OrdersResponse updateOrder(Long id, OrdersUpdateRequest order);
    OrdersResponse getOrderById(Long id);
    Orders getOrdersById(Long id);
    String deleteOrder(Long id);
    void deleteOrders(List<Orders> orders);
}
