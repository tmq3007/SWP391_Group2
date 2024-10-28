package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shoppingapp.shoppingapp.models.Orders;

import java.util.List;

@RestController
@RequestMapping(("/api/v1/orders"))
public class OrderController{

    @Autowired
    private OrderService orderService;

    // UPDATE ORDER STATUS ISPAID TO TRUE
    @PatchMapping("/isPaidToTrue/{id}")
    public ApiResponse<?> isPaidToTrue(@PathVariable("id") Long id) {
        return ApiResponse.builder()
                .result(orderService.updateIsPaidTrue(id))
                .build();
    }

    // UPDATE ORDER STATUS ISPAID TO FALSE
    @PatchMapping("/isPaidToFalse/{id}")
    public ApiResponse<?> isPaidToFalse(@PathVariable("id") Long id) {
        return ApiResponse.builder()
                .result(orderService.updateIsPaidFalse(id))
                .build();
    }

    @GetMapping("")
    public ResponseEntity<List<Orders>> getAllOrder(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable("orderId") Long orderId){
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PostMapping("")
    public ResponseEntity<Orders> addOrder(@RequestBody Orders order){
        System.out.println("G"+order.getUser().getId());
        return ResponseEntity.ok(orderService.addOrder(order));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<Orders> updateOrder(@PathVariable("orderId") Long orderId, @RequestBody Orders order){
        Orders current = orderService.getOrderById(orderId);
        if(current != null){
            current.setOrderDate(order.getOrderDate());
            current.setIsPaid(order.getIsPaid());
            current.setPaymentDate(order.getPaymentDate());
        }
        return ResponseEntity.ok(orderService.updateOrder(current));
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable("orderId") Long orderId){
        Orders current = orderService.getOrderById(orderId);
        if(current != null){
            orderService.deleteOrder(current);
        }
        return ResponseEntity.ok("Delete successful!");
    }

    @GetMapping("/total-orders")
    public ApiResponse<Integer> getTotalOrders(){
        return ApiResponse.<Integer>builder().result(orderService.getTotalOrders()).build();
    }

    @GetMapping("/byUserId/{id}")
    public ApiResponse<?> getOrdersByUserId(@PathVariable("id") Long id){
        return ApiResponse.builder().result(orderService.getOrdersByUserId(id)).build();
    }


}
