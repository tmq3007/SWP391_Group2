package com.shoppingapp.shoppingapp.dto.response;

import com.shoppingapp.shoppingapp.models.Address;
import com.shoppingapp.shoppingapp.models.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    Long id;

    String firstName;
    String lastName;

    String email;

    String username;
    String password;

    String phone;

    boolean isActive = true;

    Set<Role> roles;

    Set<Address> addresses;
}
