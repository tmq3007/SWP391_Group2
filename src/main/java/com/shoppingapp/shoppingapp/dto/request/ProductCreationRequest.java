package com.shoppingapp.shoppingapp.dto.request;

import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.models.Shop;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ProductCreationRequest {

    private String productName;
    private String category;
    private Set<String> shop;
    private String description;
    private String measurementUnit;
    private Double unitBuyPrice;
    private Double unitSellPrice;
    private Double discount;
    private int stock;
    private String pictureUrl;
    private Boolean isActive;

}
