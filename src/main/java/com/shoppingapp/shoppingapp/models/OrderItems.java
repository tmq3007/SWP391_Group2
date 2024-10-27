package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;

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

    @ManyToOne // by id
    private Orders orders;

    @ManyToOne // by shop id
    private Shop shop;

}
