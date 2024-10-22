package com.shoppingapp.shoppingapp.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UnverifiedShopCreationRequest {
    private String shopName;
    private Long user;
    private String address;
    private String city;
    private String state;
    private String country;
    private String phone;
    private String description;

}
