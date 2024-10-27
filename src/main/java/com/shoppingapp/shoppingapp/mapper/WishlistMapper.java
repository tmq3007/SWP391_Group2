package com.shoppingapp.shoppingapp.mapper;

import com.shoppingapp.shoppingapp.dto.request.WishlistRequest;
import com.shoppingapp.shoppingapp.dto.response.WishlistResponse;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    // Maps Wishlist to WishlistResponse

    WishlistResponse toWishlistResponse(Wishlist wishlist);


    Wishlist toWishlist(WishlistRequest request);


    void updateWishlist(@MappingTarget Wishlist wishlist, WishlistRequest request);


}
