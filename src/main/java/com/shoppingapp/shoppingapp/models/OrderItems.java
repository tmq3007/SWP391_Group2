package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    //Many order items in one order
    Orders order;

    @ManyToOne
    //An order item refer to a product
    //Many items in the order can refer to same product
    Product product;

    int quantity;

    double sellingPrice;

    Long userId;
}
