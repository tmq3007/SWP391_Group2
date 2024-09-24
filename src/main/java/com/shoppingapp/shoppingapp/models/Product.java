package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long productId;

     String productName;
     Long categoryId;
     Long shopId;
     String description;
     String measurementUnit;
     Double unitBuyPrice;
     Double unitSellPrice;
     Double discount;
     int stock;
     String pictureUrl;
     Boolean isActive;

}
