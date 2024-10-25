package com.shoppingapp.shoppingapp.dto.request;

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
    private List<Long> productIds;    // List of Product IDs
}
