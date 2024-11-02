package com.shoppingapp.shoppingapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemsId;

    private LocalDate orderItemsDate;
    private LocalDate orderItemsPaymentDate;
    private Boolean isPaid;
    private Long paymentId;

    private String productName;
    private String productImage;
    private String productSellPrice;
    private Double discount;
    private Long productQuantity;
    private Double itemTotalPrice;
    private Double finalPrice;

    // connect with order
    private Long orderId;
    @ManyToOne
    private Shop shop; // connect with shop
}



