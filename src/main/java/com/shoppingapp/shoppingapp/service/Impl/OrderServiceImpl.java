package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.OrdersCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.OrdersUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.OrdersResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.mapper.OrdersMapper;
import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.models.Payment;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.repository.OrderRepository;
import com.shoppingapp.shoppingapp.repository.PaymentRepository;
import com.shoppingapp.shoppingapp.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrdersMapper ordersMapper;


    @Override
    public OrdersResponse addOrder(OrdersCreationRequest order) {
        Orders orders = ordersMapper.toOrders(order);
        User user = userRepository.findById(order.getUserId()).orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));
        Payment payment = paymentRepository.findById(order.getPaymentId()).orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));
        orders.setUser(user);
        orders.setPayment(payment);
        return ordersMapper.toOrdersReponse(orderRepository.save(orders));
    }

    @Override
    public OrdersResponse getOrderById(Long id) {
        Orders orders = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));
        return ordersMapper.toOrdersReponse(orders);
    }

    @Override
    public Orders getOrdersById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));
    }

    @Override
    public OrdersResponse updateOrder(Long id,OrdersUpdateRequest order) {
        Orders orders = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));
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
        orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));
        orderRepository.deleteById(orderId);
        return "Order deleted Successfully!";
    }

    @Override
    public void deleteOrders(List<Orders> orders) {
        orderRepository.deleteAll(orders);
    }

}
