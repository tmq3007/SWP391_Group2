package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.CartCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ProductCreationRequest;
import com.shoppingapp.shoppingapp.dto.response.CartResponse;
import com.shoppingapp.shoppingapp.dto.response.ProductResponse;
import com.shoppingapp.shoppingapp.models.Cart;
import com.shoppingapp.shoppingapp.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(("/api/v1/cart"))
@AllArgsConstructor
public class CartController {
    @Autowired
    private  CartService cartService;
    @PostMapping("/{pid}/{uid}")
    public ApiResponse<CartResponse> addToCart(@PathVariable Long pid, @PathVariable Long uid) {
        ApiResponse<CartResponse> apiResponse = new ApiResponse<>(); // Khai báo kiểu dữ liệu cho ApiResponse
        try {
            CartResponse cartResponse = cartService.addToCart(pid, uid);
            apiResponse.setResult(cartResponse);

        } catch (Exception e) {

            apiResponse.setMessage(e.getMessage()); // Thông báo lỗi nếu có
        }
        return apiResponse;
    }

    @DeleteMapping("/{pid}/{uid}")
    public ApiResponse<String> deleteCart(@PathVariable Long pid, @PathVariable Long uid) {
        ApiResponse<String> apiResponse = new ApiResponse<>(); // Khai báo kiểu dữ liệu cho ApiResponse
        try {
            String message = cartService.deleteCart(pid, uid);
            apiResponse.setResult(message);

        } catch (Exception e) {

            apiResponse.setMessage(e.getMessage()); // Thông báo lỗi nếu có
        }
        return apiResponse;
    }


}
