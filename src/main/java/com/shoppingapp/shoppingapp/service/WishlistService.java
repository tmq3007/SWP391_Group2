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
        var userOp = userRepository.findById(Long.valueOf(wishlistRequest.getUserId()));
        if (!userOp.isPresent()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);  // Xử lý khi không tìm thấy user
        }
        // Create a new Wishlist object
        Wishlist wishlist = wishlistMapper.toWishlist(wishlistRequest);
        wishlist.setUser(user); // Set the user for the wishlist

        // Fetch products based on the provided IDs
        List<Product> products = productRepository.findAllById(wishlistRequest.getProductIds());
        wishlist.setProducts(products); // Set products in the wishlist

        // Save the wishlist and map it to WishlistResponse
        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        WishlistResponse wishlistResponse = new WishlistResponse();
        wishlistResponse.setUser(savedWishlist.getUser());
        wishlistResponse.setProducts(savedWishlist.getProducts());
        return wishlistResponse;
    }

    // Get all wishlists for a specific user and return a list of WishlistResponse
    public List<WishlistResponse> getWishlistsByUser(Long userId) {
        List<Wishlist> wishlists = wishlistRepository.findWishListItemByUserId(userId);
        List<WishlistResponse> wishlistResponses = new ArrayList<>();
        return wishlistMapper.toWishlistResponseList(wishlists); // Map to response list
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

    // Update an existing wishlist using WishlistRequest and return WishlistResponse
    public WishlistResponse updateWishlist(Long wishlistId, WishlistRequest wishlistRequest) {
        return wishlistRepository.findById(wishlistId).map(wishlist -> {
            // Find the user by ID
            User user = userRepository.findById(wishlistRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            wishlist.setUser(user); // Update the user

            // Fetch and set products based on the provided IDs
            List<Product> products = productRepository.findAllById(wishlistRequest.getProductIds());
            wishlist.setProducts(products); // Update products in the wishlist

            // Save the updated wishlist and map it to WishlistResponse
            Wishlist updatedWishlist = wishlistRepository.save(wishlist);
            return wishlistMapper.toWishlistResponse(updatedWishlist);
        }).orElseThrow(() -> new RuntimeException("Wishlist not found")); // Exception if not found
    }
}
