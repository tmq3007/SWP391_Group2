package com.shoppingapp.shoppingapp.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewCreationRequest {

    String reviewText;
    Double rating;
    Long productId;
    Long userId;

}
