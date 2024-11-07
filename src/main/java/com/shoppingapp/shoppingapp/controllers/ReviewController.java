package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.ProductCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ReviewCreationRequest;
import com.shoppingapp.shoppingapp.dto.response.ProductResponse;
import com.shoppingapp.shoppingapp.dto.response.ReviewResponse;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Review;
import com.shoppingapp.shoppingapp.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/reviews")
@AllArgsConstructor

public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    //get all review by product id
    @GetMapping("/get-all-review-by-product-id/{productId}")
    public ResponseEntity<List<Review>> getAllReviewsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getAllReviewsByProductId(productId));
    }

    //create review
    //Build Add Product REST API
    @PostMapping()
    public ApiResponse<ReviewResponse> createReview(@RequestBody ReviewCreationRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(reviewService.createReview(request));
        return apiResponse;
    }

    @GetMapping("/get-all-review-by-shop-id/{shopId}")
    public ResponseEntity<List<Review>> getAllReviewsByShopId(@PathVariable Long shopId) {
        return ResponseEntity.ok(reviewService.getAllReviewsByShopId(shopId));
    }

}
