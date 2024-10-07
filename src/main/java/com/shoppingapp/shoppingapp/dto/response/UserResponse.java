package com.shoppingapp.shoppingapp.dto.response;

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

    Boolean isActive;

    Set<Role> roles;
}
