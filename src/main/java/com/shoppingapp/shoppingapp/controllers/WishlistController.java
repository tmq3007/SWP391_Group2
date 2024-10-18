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
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(wishlistService.createWishlist(wishlistRequest));
        return apiResponse;
    }

    // Endpoint to get all wishlists for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WishlistResponse>> getUserWishlists(@PathVariable Long userId) {
        List<WishlistResponse> wishlists = wishlistService.getWishlistsByUser(userId);
        return ResponseEntity.ok(wishlists);
    }

    // Endpoint to get a wishlist by ID
    @GetMapping("/{wishlistId}")
    public ResponseEntity<WishlistResponse> getWishlistById(@PathVariable Long wishlistId) {
        WishlistResponse wishlist = wishlistService.getWishlistById(wishlistId);
        return ResponseEntity.ok(wishlist);
    }

    // Endpoint to delete a wishlist by ID
    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long wishlistId) {
        wishlistService.deleteWishlist(wishlistId);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to update an existing wishlist
    @PutMapping("/{wishlistId}")
    public ResponseEntity<WishlistResponse> updateWishlist(
            @PathVariable Long wishlistId,
            @RequestBody WishlistRequest wishlistRequest) {
        WishlistResponse updatedWishlist = wishlistService.updateWishlist(wishlistId, wishlistRequest);
        return ResponseEntity.ok(updatedWishlist);
    }
}
