package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.ReviewCreationRequest;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.mapper.ReviewMapper;
import com.shoppingapp.shoppingapp.models.Review;
import com.shoppingapp.shoppingapp.repository.ProductRepository;
import com.shoppingapp.shoppingapp.repository.ReviewRepository;
import com.shoppingapp.shoppingapp.repository.UserRepository;
import com.shoppingapp.shoppingapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewMapper reviewMapper;


    @Override
    public Review createReview(ReviewCreationRequest request) {
        // Kiểm tra product id
        var productOptional = productRepository.findById(request.getProductId());
        if (productOptional.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_EXISTED);
        }

        // Kiểm tra user
        var userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        Review review = reviewMapper.toReview(request);
        review.setUser(userOptional.get());
        review.setProduct(productOptional.get());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviewsByProductId(Long productId) {
        return reviewRepository.findAllReviewsByProductId(productId);
    }
}
