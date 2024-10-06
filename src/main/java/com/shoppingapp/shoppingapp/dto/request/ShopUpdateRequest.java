package com.shoppingapp.shoppingapp.dto.request;
import com.shoppingapp.shoppingapp.models.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopUpdateRequest {
    private String shopName;
    private String address;
    private String city;
    private String state;
    private String country;
    private String phone;
    private String description;
    private String logo;
    private String cover;


}
