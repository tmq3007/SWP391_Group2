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
    @Mapping(source = "user", target = "user") // Ensure user is mapped if needed
    @Mapping(source = "products", target = "products") // Ensure products are mapped if needed
    WishlistResponse toWishlistResponse(Wishlist wishlist);

    // Maps WishlistRequest to Wishlist
    @Mapping(target = "user", ignore = true) // Ignore user; will be set later in service
    @Mapping(target = "products", ignore = true) // Ignore products; will be set later in service
    Wishlist toWishlist(WishlistRequest request);

    // Update an existing Wishlist from WishlistRequest
    @Mapping(target = "user", ignore = true) // Ignore user; will be set later in service
    @Mapping(target = "products", ignore = true) // Ignore products when updating
    void updateWishlist(@MappingTarget Wishlist wishlist, WishlistRequest request);

    // Maps a list of Wishlists to a list of WishlistResponses
    List<WishlistResponse> toWishlistResponseList(List<Wishlist> wishlists);

    // Optionally, you could add this method to map Product to ProductResponse if needed
    // ProductResponse toProductResponse(Product product);
}
