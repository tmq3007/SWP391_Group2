package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.CartCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.CartUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.CartResponse;
import com.shoppingapp.shoppingapp.models.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getAllCartsByUserId(Long userId);


    CartResponse addToCart(Long productId,Long userId);

    String deleteCart(Long productId,Long userId);
}
