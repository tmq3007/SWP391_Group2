package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.models.OrderItems;
import com.shoppingapp.shoppingapp.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orderItems")
public class OrderItemsController {

    @Autowired
    private OrderItemsService orderItemsService;

    @GetMapping
    ApiResponse<?> findAll() {
        return ApiResponse.builder().result(orderItemsService.getAll()).build();
    }

    @GetMapping("/{id}")
    ApiResponse<?> findById(@PathVariable Long id) {
        return ApiResponse.builder().result(orderItemsService.getByOrderId(id)).build();
    }

    @GetMapping("/getAllByShopId/{id}")
    ApiResponse<?> findAllByShopId(@PathVariable("id") Long id) {
        System.out.println("Get all by shop id");
        return ApiResponse.builder().result(orderItemsService.getAllByShopId(id)).build();
    }

    @PostMapping("/{id}")
    ApiResponse<?> add(@RequestBody OrderItems orderItems, @PathVariable Long id) {
        System.out.println("Order id: "+id);
        return ApiResponse.builder().result(orderItemsService.add(orderItems,id)).build();
    }

}
