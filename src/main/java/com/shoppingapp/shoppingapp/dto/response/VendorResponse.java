package com.shoppingapp.shoppingapp.dto.response;

import com.shoppingapp.shoppingapp.models.Role;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.models.UnverifiedShop;
import jakarta.annotation.Nullable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VendorResponse {
    Long id;

    String firstName;
    String lastName;

    String email;

    String phone;

    Boolean isActive;

    @Nullable
    Shop shop;

    @Nullable
    UnverifiedShop unverifiedShop;
}
