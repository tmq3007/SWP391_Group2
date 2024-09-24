package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.OrdersCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.OrdersUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.OrdersResponse;
import com.shoppingapp.shoppingapp.mapper.OrdersMapper;
import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.repository.OrderRepository;
import com.shoppingapp.shoppingapp.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrdersService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrdersMapper ordersMapper;


    @Override
    public OrdersResponse addOrder(OrdersCreationRequest order) {
        Orders orders = ordersMapper.toOrders(order);
        return ordersMapper.toOrdersReponse(orderRepository.save(orders));
    }

    @Override
    public OrdersResponse getOrderById(Long id) {
        Orders orders = orderRepository.findById(id).orElseThrow(null);
        return ordersMapper.toOrdersReponse(orders);
    }

    @Override
    public Orders getOrdersById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public OrdersResponse updateOrder(Long id,OrdersUpdateRequest order) {
        Orders orders = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        ordersMapper.updateOrders(orders,order);
        return ordersMapper.toOrdersReponse(orderRepository.save(orders));
    }


    @Override
    public List<OrdersResponse> getAllOrders() {
        List<Orders> orders = orderRepository.findAll();
        List<OrdersResponse> ordersRepons = new ArrayList<>();
        for(Orders order : orders){
            ordersRepons.add(ordersMapper.toOrdersReponse(order));
        }
        return ordersRepons;
    }



    @Override
    public String deleteOrder(Long orderId) {
        try{
            orderRepository.deleteById(orderId);
        }catch (Exception e){
            return "Order not found";
        }
        return "Order deleted";
    }

    @Override
    public void deleteOrders(List<Orders> orders) {
        orderRepository.deleteAll(orders);
    }

}
