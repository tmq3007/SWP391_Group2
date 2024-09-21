package com.shoppingapp.shoppingapp.dto.request;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    String firstName;
    String lastName;

    String email;

    String username;
    String password;

    String phone;

}
