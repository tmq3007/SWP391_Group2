package com.shoppingapp.shoppingapp.dto.response;
import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Review;
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
      Long shopID;
      String shopName;
      User user;
      Set<Product> products;
      Set<Orders> order;
      String address;
      String city;
      String district;
      String subdistrict;
      String description;
      String phone;
      String logo;
      String cover;

}
