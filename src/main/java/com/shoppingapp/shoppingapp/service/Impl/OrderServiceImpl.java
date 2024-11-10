package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.controllers.OrderItemsController;
import com.shoppingapp.shoppingapp.models.OrderItems;
import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.repository.OrderItemRepository;
import com.shoppingapp.shoppingapp.repository.OrderRepository;
import com.shoppingapp.shoppingapp.repository.UserRepository;
import com.shoppingapp.shoppingapp.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemsController orderItemsController;

    @Override
    @Transactional
    public Orders addOrder(Orders order) {
        try {
            User user = userRepository.findById(order.getUser().getId()).orElse(null);
            if (user == null) {
                System.out.println("Null user not found");
                return null;
            } else {
                System.out.println("User existed");
                order.setUser(user);
                System.out.println("Saving order to database...");
                Orders o = orderRepository.save(order);
                System.out.println("Order saved successfully!");
                System.out.println("O id: " + o.getOrderId());
                return o;
            }
        } catch (Exception e) {
            e.printStackTrace();  // Kiểm tra xem có exception không
            return null;
        }
    }

    @Override
    public Orders updateOrder(Orders order) {
        // update ispaid - odate - pdate - paymentid
        List<OrderItems> list = orderItemRepository.findAll()
                .stream().filter(a -> a.getOrderId() == order.getOrderId()).toList();
        System.out.println("Result - "+order.getPaymentId());
        System.out.println("Order "+order.getOrderId() + order.getPaymentId());

        list.forEach((a) -> {a.setOrderItemsDate(order.getOrderDate());
            a.setOrderItemsPaymentDate(order.getPaymentDate());
            a.setIsPaid(order.getIsPaid());
            a.setPaymentId(order.getPaymentId());});
        orderItemRepository.saveAll(list);
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
        List<OrderItems> itemsToDelete = orderItemRepository.findAll()
                .stream()
                .filter(a -> a.getOrderId() == order.getOrderId())
                .toList();
        orderItemRepository.deleteAll(itemsToDelete);
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
        List<OrderItems> list = orderItemRepository.findAll()
                .stream().filter(a -> a.getOrderId() == order.getOrderId()).toList();
        System.out.println("Order "+order.getOrderId() + order.getPaymentId());
        list.forEach((a) -> {a.setOrderItemsDate(order.getOrderDate());
            LocalDate date = LocalDate.now();
            a.setOrderItemsPaymentDate(date);
            a.setIsPaid(true);
            a.setPaymentId(order.getPaymentId());});
            orderItemRepository.saveAll(list);

            order.setIsPaid(true);
            orderRepository.save(order);
            return "Order IsPaid to TRUE";
        }
        return "Order not found!";
    }
    @Override
    public String updateIsPaidFalse(Long id) {
        Orders order = orderRepository.findById(id).orElse(null);


        List<OrderItems> list = orderItemRepository.findAll()
                .stream().filter(a -> a.getOrderId() == order.getOrderId()).toList();

        System.out.println("Order "+order.getOrderId() + order.getPaymentId());


        LocalDate date = LocalDate.now();
        list.forEach((a) -> {a.setOrderItemsDate(order.getOrderDate());
            a.setOrderItemsPaymentDate(date);
            a.setIsPaid(true);
            a.setPaymentId(order.getPaymentId());});
        orderItemRepository.saveAll(list);


        if(order != null) {
            order.setIsPaid(true);
            orderRepository.save(order);
            return "Order IsPaid to TRUE";
        }


        if(order != null) {
            order.setIsPaid(false);
            orderRepository.save(order);
            return "Order IsPaid to TRUE";
        }
        return "Order not found!";
    }

    @Override
    public List<Object[]> countOrdersByMonthAndYear() {
        log.info("Information: " + orderRepository.countOrdersByMonthAndYear());
        return orderRepository.countOrdersByMonthAndYear();
    }

    @Override
    public Double sumTotalRevenue() {
        return orderRepository.sumTotalRevenue();
    }
}
