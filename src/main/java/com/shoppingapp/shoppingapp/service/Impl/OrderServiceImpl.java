package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.repository.OrderRepository;
import com.shoppingapp.shoppingapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Orders addOrder(Orders order) {
        return orderRepository.save(order);
    }

    @Override
    public Orders updateOrder(Orders order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Orders> getAllOrders() {
        return (List<Orders>) orderRepository.findAll();
    }

    @Override
    public Orders getOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public void deleteOrder(Orders order) {
        orderRepository.delete(order);
    }

    @Override
    public void deleteOrders(List<Orders> orders) {
        orderRepository.deleteAll(orders);
    }

    @Override
    public int getTotalOrders() {
        return (int) orderRepository.count();
    }

    @Override
    public List<Orders> getOrdersByUserId(Long id) {
        return orderRepository.findAll()
                .stream()
                .filter(order -> order.getUser().getId().equals(id))
                .toList();
    }

    @Override
    public String updateIsPaidTrue(Long id) {
        Orders order = orderRepository.findById(id).orElse(null);
        if(order != null) {
            order.setIsPaid(true);
            orderRepository.save(order);
            return "Order IsPaid to TRUE";
        }
        return "Order not found!";
    }
    @Override
    public String updateIsPaidFalse(Long id) {
        Orders order = orderRepository.findById(id).orElse(null);
        if(order != null) {
            order.setIsPaid(false);
            orderRepository.save(order);
            return "Order IsPaid to TRUE";
        }
        return "Order not found!";
    }
}
