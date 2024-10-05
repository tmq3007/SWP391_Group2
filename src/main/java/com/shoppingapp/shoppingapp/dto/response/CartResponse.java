package com.shoppingapp.shoppingapp.dto.response;

import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.User;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    Long cartId;

    User user;

    Product product;

    int quantity;

    Double totalPrice;

}
