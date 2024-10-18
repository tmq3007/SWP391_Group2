package com.shoppingapp.shoppingapp.controller;
import com.shoppingapp.shoppingapp.models.Wishlist;
import com.shoppingapp.shoppingapp.dto.request.WishlistRequest;
import com.shoppingapp.shoppingapp.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    // Endpoint to create a new wishlist
    @PostMapping
    public ResponseEntity<Wishlist> addWishlist(@RequestBody WishlistRequest wishlistRequest) {
        Wishlist createdWishlist = wishlistService.createWishlist(wishlistRequest);
        return ResponseEntity.ok(createdWishlist);
    }

    // Endpoint to get all wishlists for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Wishlist>> getUserWishlists(@PathVariable Long userId) {
        List<Wishlist> wishlists = wishlistService.getWishlistsByUser(userId);
        return ResponseEntity.ok(wishlists);
    }

    // Endpoint to delete a wishlist by ID
    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long wishlistId) {
        wishlistService.deleteWishlist(wishlistId);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to update an existing wishlist
    @PutMapping("/{wishlistId}")
    public ResponseEntity<Wishlist> updateWishlist(
            @PathVariable Long wishlistId,
            @RequestBody WishlistRequest wishlistRequest) {
        Wishlist updatedWishlist = wishlistService.updateWishlist(wishlistId, wishlistRequest);
        return ResponseEntity.ok(updatedWishlist);
    }

    // Endpoint to get a wishlist by ID (Optional)
    @GetMapping("/{wishlistId}")
    public ResponseEntity<Wishlist> getWishlistById(@PathVariable Long wishlistId) {
        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);
        if (wishlist != null) {
            return ResponseEntity.ok(wishlist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
