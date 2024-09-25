package com.shoppingapp.shoppingapp.dto.response;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopResponse {
    public String shopName;
    public String address;
    public String city;
    public String state;
    public String country;
    public String phone;


}
