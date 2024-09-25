package com.shoppingapp.shoppingapp.dto.response;

import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.models.Shop;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {

    private Long productId;
    private String productName;
    private Category category;
    private Shop shop;
    private String description;
    private String measurementUnit;
    private Double unitBuyPrice;
    private Double unitSellPrice;
    private Double discount;
    private int stock;
    private String pictureUrl;
    private Boolean isActive;
}
