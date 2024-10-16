package com.shoppingapp.shoppingapp.dto.response;
import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopResponse {
    private Long shopID;
    private String shopName;
    private User user;
    private Set<Product> products;
    private Set<Orders> order;
    private String address;
    private String city;
    private String district;
    private String subdistrict;
    private String phone;
    private String logo;
    private String cover;

}
