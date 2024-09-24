package com.shoppingapp.shoppingapp.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    String firstName;
    String lastName;

    String email;

    String username;
    String password;

    boolean isActive;

    Set<String> Roles;

}
