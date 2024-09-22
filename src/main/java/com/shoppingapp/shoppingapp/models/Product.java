package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long shopId;
    private String description;
    private String measurementUnit;
    private Double unitBuyPrice;
    private Double unitSellPrice;
    private Double discount;
    private int stock;
    private String pictureUrl;
    private Boolean isActive;

}
