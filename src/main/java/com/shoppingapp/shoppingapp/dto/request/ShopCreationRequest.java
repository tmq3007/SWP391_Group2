package com.shoppingapp.shoppingapp.dto.request;
import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopCreationRequest {
    private String shopName;

    private Long user;
    private Set<Long> products;
    private Set<Long> order;
    private String address;
    private String city;
    private String state;
    private String country;
    private String phone;



}
