package com.shoppingapp.shoppingapp.controller;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.WishlistRequest;
import com.shoppingapp.shoppingapp.dto.response.WishlistResponse;
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
    public ApiResponse<List<WishlistResponse>> getUserWishlists(@PathVariable Long userId) {
        ApiResponse<List<WishlistResponse>> apiResponse = new ApiResponse<>();
        List<WishlistResponse> wishlists = wishlistService.getWishlistsByUser(userId);
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
    @DeleteMapping("/{wishlistId}")
    public ApiResponse<Void> deleteWishlist(@PathVariable Long wishlistId) {
        wishlistService.deleteWishlist(wishlistId);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setResult(null);  // No content to return
        return apiResponse;
    }

    // Endpoint to update an existing wishlist
    @PutMapping("/{wishlistId}")
    public ApiResponse<WishlistResponse> updateWishlist(
            @PathVariable Long wishlistId,
            @RequestBody WishlistRequest wishlistRequest) {
        ApiResponse<WishlistResponse> apiResponse = new ApiResponse<>();
        WishlistResponse updatedWishlist = wishlistService.updateWishlist(wishlistId, wishlistRequest);
        apiResponse.setResult(updatedWishlist);
        return apiResponse;
    }
}
