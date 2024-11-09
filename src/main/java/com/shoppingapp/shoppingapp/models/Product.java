package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false )
    private Shop shop;


    private String description;
    private String measurementUnit;
    private Double unitBuyPrice;
    private Double unitSellPrice;
    private Double discount;
    private int stock;
    private String pictureUrl;
    private String pictureUrl2;
    private Boolean isActive;
    private Double averageRating;


}
