package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.models.Wishlist;
import com.shoppingapp.shoppingapp.dto.request.WishlistRequest;
import com.shoppingapp.shoppingapp.repository.UserRepository;
import com.shoppingapp.shoppingapp.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository; // Added to fetch User

    // Create a new wishlist using WishlistRequest
    public Wishlist createWishlist(WishlistRequest wishlistRequest) {
        // Find the user by ID
        User user = userRepository.findById(wishlistRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new Wishlist object
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);

        // Convert product IDs to Product entities
        List<Product> products = wishlistRequest.getProductIds().stream()
                .map(productId -> {
                    Product product = new Product(); // Assuming Product has a no-arg constructor
                    product.setProductId(productId); // Set the product ID
                    return product;
                })
                .collect(Collectors.toList());

        wishlist.setProduct(products); // Set the products in the wishlist

        return wishlistRepository.save(wishlist); // Save and return the created wishlist
    }

    // Get all wishlists for a specific user
    public List<Wishlist> getWishlistsByUser(Long userId) {
        return wishlistRepository.findWishListItemByUserId(userId);
    }

    // Get a wishlist by its ID
    public Wishlist getWishlistById(Long wishlistId) {
        return wishlistRepository.findById(wishlistId).orElse(null);
    }

    // Delete a wishlist
    public void deleteWishlist(Long wishlistId) {
        wishlistRepository.deleteById(wishlistId);
    }

    // Update an existing wishlist using WishlistRequest
    public Wishlist updateWishlist(Long wishlistId, WishlistRequest wishlistRequest) {
        return wishlistRepository.findById(wishlistId).map(wishlist -> {
            // Find the user by ID
            User user = userRepository.findById(wishlistRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            wishlist.setUser(user);

            // Convert product IDs to Product entities
            List<Product> products = wishlistRequest.getProductIds().stream()
                    .map(productId -> {
                        Product product = new Product(); // Assuming Product has a no-arg constructor
                        product.setProductId(productId); // Set the product ID
                        return product;
                    })
                    .collect(Collectors.toList());

            wishlist.setProduct(products); // Update the products in the wishlist

            return wishlistRepository.save(wishlist); // Save and return the updated wishlist
        }).orElse(null);
    }
}
