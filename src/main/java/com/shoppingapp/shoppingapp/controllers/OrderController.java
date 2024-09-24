package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.OrdersCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.OrdersUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.OrdersResponse;
import com.shoppingapp.shoppingapp.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shoppingapp.shoppingapp.models.Orders;

import java.util.List;

@RestController
@RequestMapping(("/api/v1/orders"))
public class OrderController{

    @Autowired
    private OrdersService orderService;

    @GetMapping("")
    public ResponseEntity<List<OrdersResponse>> getAllOrder(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersResponse> getOrderById(@PathVariable("orderId") Long orderId){
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PostMapping("")
    public ResponseEntity<OrdersResponse> addOrder(@RequestBody OrdersCreationRequest order){
        return ResponseEntity.ok(orderService.addOrder(order));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrdersResponse> updateOrder(@PathVariable("orderId") Long orderId, @RequestBody OrdersUpdateRequest order)    {
        return ResponseEntity.ok(orderService.updateOrder(orderId,order));
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable("orderId") Long orderId){
        return ResponseEntity.ok(orderService.deleteOrder(orderId));
    }

}
