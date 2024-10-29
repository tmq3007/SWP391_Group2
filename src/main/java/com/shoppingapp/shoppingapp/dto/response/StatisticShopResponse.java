package com.shoppingapp.shoppingapp.dto.response;

import com.shoppingapp.shoppingapp.models.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatisticShopResponse {
    Long shopID;
    String shopName;
    User user;
    Long totalProduct;
    Long totalOrder;
    String address;
    String city;
    String state;
    String country;
    String phone;
    String description;
    String logo;
    String cover;
}
