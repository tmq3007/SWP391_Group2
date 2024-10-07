package com.shoppingapp.shoppingapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long  id;

    @ManyToOne
    @JsonIgnore
     Cart cart;

    @ManyToOne
    Product product;

    String buyUnit;

    int quantity=1;

    Double totalPrice;

    Long userId;

}
