package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemsId;

    private String productName;
    private String productImage;
    private String productSellPrice;
    private Double discount;
    private Long productQuantity;
    private Double itemTotalPrice;
    private Double finalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
