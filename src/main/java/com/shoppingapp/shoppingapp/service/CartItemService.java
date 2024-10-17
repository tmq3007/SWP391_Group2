package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.models.CartItem;

public interface CartItemService {
    CartItem updateCartItem(Long userId,Long id,CartItem cartItem)  ;
    void removeCartItem(Long userId,Long cartItemId)  ;
    CartItem findCartItemById(Long id)  ;
}
