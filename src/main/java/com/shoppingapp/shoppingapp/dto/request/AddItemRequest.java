package com.shoppingapp.shoppingapp.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddItemRequest {
    private String buyUnit;
    private int quantity;
    private Long productId;
}
