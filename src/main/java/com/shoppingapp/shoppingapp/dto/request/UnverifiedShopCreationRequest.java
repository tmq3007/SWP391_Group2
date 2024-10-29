package com.shoppingapp.shoppingapp.dto.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UnverifiedShopCreationRequest {
    private String shopName;
    private Long user;
    String address;
    String city;
    String district;
    String subdistrict;
    String description;
    String phone;
    String logo;
    String cover;
}
