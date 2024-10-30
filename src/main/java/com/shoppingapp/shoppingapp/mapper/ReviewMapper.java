package com.shoppingapp.shoppingapp.mapper;

import com.shoppingapp.shoppingapp.dto.request.ProductCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ReviewCreationRequest;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "product",ignore = true)
    @Mapping(target = "user", ignore = true)
    Review toReview(ReviewCreationRequest reviewCreationRequest);

}
