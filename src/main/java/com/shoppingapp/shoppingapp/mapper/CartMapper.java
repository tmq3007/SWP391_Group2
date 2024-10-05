package com.shoppingapp.shoppingapp.mapper;

import com.shoppingapp.shoppingapp.dto.request.CartCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.CartUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.CartResponse;
import com.shoppingapp.shoppingapp.models.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface CartMapper {
    Cart toCart(CartCreationRequest cartCreationRequest);
    //void updateCart(@MappingTarget Cart cart, CartUpdateRequest cartUpdateRequest);

    CartResponse toCartResponse(Cart cart);
}
