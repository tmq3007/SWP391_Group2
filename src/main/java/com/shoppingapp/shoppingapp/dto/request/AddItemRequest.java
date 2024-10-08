package com.shoppingapp.shoppingapp.dto.request;

import lombok.Data;

@Data
public class AddItemRequest {
    private String buyUnit;
    private int quantity;
    private Long productId;
}
