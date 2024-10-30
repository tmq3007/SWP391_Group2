package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.ReviewCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.response.ReviewResponse;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Review;

import java.util.List;

public interface ReviewService {
    Review createReview(ReviewCreationRequest request);
    //get all review product id have
    List<Review> getAllReviewsByProductId(Long productId);
}
