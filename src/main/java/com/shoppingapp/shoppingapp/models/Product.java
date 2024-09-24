package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

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
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
     Category category;
    @ManyToMany
     Set<Shop> shop;
     String description;
     String measurementUnit;
     Double unitBuyPrice;
     Double unitSellPrice;
     Double discount;
     int stock;
     String pictureUrl;
     Boolean isActive;

}
