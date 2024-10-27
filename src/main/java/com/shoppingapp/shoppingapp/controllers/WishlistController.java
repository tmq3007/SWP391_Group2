package com.shoppingapp.shoppingapp.controller;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.WishlistRequest;
import com.shoppingapp.shoppingapp.dto.response.WishlistResponse;
import com.shoppingapp.shoppingapp.models.Wishlist;
import com.shoppingapp.shoppingapp.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    // Endpoint to create a new wishlist
    @PostMapping
    public ApiResponse<WishlistResponse> addWishlist(@RequestBody WishlistRequest wishlistRequest) {
        ApiResponse<WishlistResponse> apiResponse = new ApiResponse<>();
        WishlistResponse createdWishlist = wishlistService.createWishlist(wishlistRequest);
        apiResponse.setResult(createdWishlist);
        return apiResponse;
    }

    // Endpoint to get all wishlists for a user
    @GetMapping("/user/{userId}")
    public ApiResponse<WishlistResponse> getUserWishlist(@PathVariable Long userId) {
        ApiResponse<WishlistResponse> apiResponse = new ApiResponse<>();
        WishlistResponse wishlists = wishlistService.getWishlistByUserId(userId);
        apiResponse.setResult(wishlists);
        return apiResponse;
    }

    // Endpoint to get a wishlist by ID
    @GetMapping("/{wishlistId}")
    public ApiResponse<WishlistResponse> getWishlistById(@PathVariable Long wishlistId) {
        ApiResponse<WishlistResponse> apiResponse = new ApiResponse<>();
        WishlistResponse wishlist = wishlistService.getWishlistById(wishlistId);
        apiResponse.setResult(wishlist);
        return apiResponse;
    }

    // Endpoint to delete a wishlist by ID

    @DeleteMapping("/user/{userId}/{productId}")
    public ApiResponse<WishlistResponse> deleteWishlistProduct(@PathVariable Long userId, @PathVariable Long productId) {
        WishlistResponse wishlist = wishlistService.deleteWishlistItem(userId, productId);
        ApiResponse<WishlistResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(wishlist);  // No content to return
        return apiResponse;
    }


}
