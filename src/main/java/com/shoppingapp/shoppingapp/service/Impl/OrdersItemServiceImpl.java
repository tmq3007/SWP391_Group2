package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.models.OrderItems;
import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.repository.OrderItemRepository;
import com.shoppingapp.shoppingapp.repository.OrderRepository;
import com.shoppingapp.shoppingapp.repository.ShopRepository;
import com.shoppingapp.shoppingapp.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersItemServiceImpl implements OrderItemsService {


    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public List<OrderItems> getAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public List<OrderItems> getByOrderId(Long id) {
        Orders orders = orderRepository.findById(id).orElse(null);
        if (orders == null) {
            System.out.println("Get order items by order id FAILED!");
            return Collections.emptyList(); // Return an empty list instead of null
        } else {
            System.out.println("Success!");
            return orderItemRepository.findAll().stream()
                    .filter(item -> item.getOrderId() != null && // Check if orders is not null
                            item.getOrderId() != null &&
                            item.getOrderId().equals(id))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public String add(OrderItems orderItems) {
        Orders orders = orderRepository.findById(orderItems.getOrderId()).orElse(null);
        if(orders == null){
            System.out.println("No order found!");
            return "No order found!";
        }else{
            // set back to it father
            System.out.println("Add order items");
            orderItems.setOrderItemsDate(orders.getOrderDate());
            orderItems.setOrderItemsPaymentDate(orders.getPaymentDate());
            orderItems.setIsPaid(orders.getIsPaid());
            orderItems.setPaymentId(orders.getPaymentId());
            OrderItems a = orderItemRepository.save(orderItems);
            List<OrderItems> list = orders.getOrderItemsList();
            list.add(a);
            orders.setOrderItemsList(list);
            orderRepository.save(orders);
            return "Success!";
        }
    }

    @Override
    public List<OrderItems> getAllByShopId(Long id) {
        return orderItemRepository.findAll()
                .stream()
                .filter((a) -> a.getShop().getShopId().equals(id)).toList();
    }

    @Override
    public OrderItems getById(Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    @Override
    public String updateIsPaidTrue(Long id) {
        OrderItems orderItems = orderItemRepository.findById(id).orElse(null);
        if(orderItems != null) {
            orderItems.setIsPaid(true);
            orderItemRepository.save(orderItems);
            return "Order IsPaid to TRUE";
        }
        return "Order not found!";
    }
}
