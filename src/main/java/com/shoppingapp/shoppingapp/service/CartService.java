package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.CartCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.CartUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.CartResponse;
import com.shoppingapp.shoppingapp.models.Cart;
import com.shoppingapp.shoppingapp.models.CartItem;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.User;

import java.util.List;

public interface CartService {

    public CartItem addCartItem(
            User user,
            Product product,
            String unitBuy,
            int  quantity
    );

    public Cart findUserCart(User user);
}
