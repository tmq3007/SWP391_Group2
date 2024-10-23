package com.shoppingapp.shoppingapp.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopCreationRequest {
    private String shopName;
    private Long user;
    private String address;
    private String city;
    private String district;
    private String subdistrict;
    private String phone;
    private String description;
    private String logo;
    private String cover;

}
