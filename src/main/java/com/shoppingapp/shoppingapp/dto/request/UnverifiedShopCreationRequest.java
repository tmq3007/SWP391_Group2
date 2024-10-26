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
    private String address;
    private String city;
    private String state;
    private String country;
    private String phone;
    private String description;
    private String logo;
    private String cover;
}
