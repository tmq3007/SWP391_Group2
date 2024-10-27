package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.models.Wishlist;
import com.shoppingapp.shoppingapp.dto.request.WishlistRequest;
import com.shoppingapp.shoppingapp.dto.response.WishlistResponse; // Import the WishlistResponse
import com.shoppingapp.shoppingapp.mapper.WishlistMapper; // Import your mapper
import com.shoppingapp.shoppingapp.repository.ProductRepository;
import com.shoppingapp.shoppingapp.repository.UserRepository;
import com.shoppingapp.shoppingapp.repository.WishlistRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WishlistMapper wishlistMapper; // Inject the WishlistMapper

    // Create a new wishlist using WishlistRequest and return WishlistResponse
    public WishlistResponse createWishlist(WishlistRequest wishlistRequest) {
        // Find the user by ID
        var user = userRepository.findById(wishlistRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Retrieve the user's wishlist or create a new one if it doesn't exist
        Wishlist wishlist = wishlistRepository.findWishlistByUserId(user.getId());
        if (wishlist == null) {
            wishlist = new Wishlist();
            wishlist.setUser(user); // Set the user for the new wishlist
            wishlist.setProducts(new ArrayList<>()); // Initialize the products list
        } else if (wishlist.getProducts() == null) {
            wishlist.setProducts(new ArrayList<>()); // Initialize the products list if it's null
        }

        // Fetch the product based on the provided ID
        Product product = productRepository.findById(wishlistRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Add the product to the wishlist
        wishlist.getProducts().add(product);

        // Save the wishlist
        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        // Prepare the response
        WishlistResponse wishlistResponse = new WishlistResponse();
        wishlistResponse.setUser(savedWishlist.getUser());
        wishlistResponse.setProducts(savedWishlist.getProducts());

        return wishlistResponse;
    }



    // Get all wishlists for a specific user and return a list of WishlistResponse
    public WishlistResponse getWishlistByUserId(Long userId) {
        Wishlist wishlist = wishlistRepository.findWishlistByUserId(userId);
        //WishlistResponse wishlistResponses = new ArrayList<>();
        return wishlistMapper.toWishlistResponse(wishlist); // Map to response list
    }

    // Get a wishlist by its ID and return WishlistResponse
    public WishlistResponse getWishlistById(Long wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        return wishlistMapper.toWishlistResponse(wishlist); // Map to response DTO
    }

    // Delete a wishlist
    public void deleteWishlist(Long wishlistId) {
        wishlistRepository.deleteById(wishlistId);
    }

    public WishlistResponse deleteWishlistItem(Long userId, Long productId) {
        var product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Wishlist wishlist = wishlistRepository.findWishlistByUserId(userId);
        wishlist.getProducts().remove(product);
        wishlistRepository.save(wishlist);
        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        return wishlistMapper.toWishlistResponse(wishlist);
    }
}
