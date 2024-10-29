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

    /*@Override
    public List<OrderItems> getByOrderId(Long id) {
        Orders orders = orderRepository.findById(id).orElse(null);
        if(orders == null){
            System.out.println("Get order items by order id FAILED!");
            return null;
        }else{
            System.out.println("Success !");
            return orderItemRepository.findAll().stream()
                    .filter(item -> item.getOrders().getOrderId().equals(id))
                    .collect(Collectors.toList());
        }
    }*/

    @Override
    public List<OrderItems> getByOrderId(Long id) {
        Orders orders = orderRepository.findById(id).orElse(null);
        if (orders == null) {
            System.out.println("Get order items by order id FAILED!");
            return Collections.emptyList(); // Return an empty list instead of null
        } else {
            System.out.println("Success!");
            return orderItemRepository.findAll().stream()
                    .filter(item -> item.getOrders() != null && // Check if orders is not null
                            item.getOrders().getOrderId() != null && // Check if orderId is not null
                            item.getOrders().getOrderId().equals(id)) // Now safe to compare
                    .collect(Collectors.toList());
        }
    }

    @Override
    public String add(OrderItems orderItems,Long orderId) {
        Orders orders = orderRepository.findById(orderId).orElse(null);
        if(orders == null){
            System.out.println("No order found!");
            return "No order found!";
        }else{
            orderItemRepository.save(orderItems);
            System.out.println("Order added succcessfully!");
            return "OrderItems added!";
        }
    }

    @Override
    public List<OrderItems> getAllByShopId(Long id) {
        return orderItemRepository.findAll()
                .stream()
                .filter((a) -> a.getShop().getShopId().equals(id)).toList();
    }
}
