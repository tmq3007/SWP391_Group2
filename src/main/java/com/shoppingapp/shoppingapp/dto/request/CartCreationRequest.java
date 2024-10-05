package com.shoppingapp.shoppingapp.dto.request;

import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartCreationRequest {

    Long cartId;

    User user;

    Product product;

    int quantity;

    Double totalPrice;
}
