package com.shoppingapp.shoppingapp.dto.request;

import com.shoppingapp.shoppingapp.models.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WishlistRequest {
    private Long userId;              // User ID
    private Long productId;    // List of Product IDs
}
