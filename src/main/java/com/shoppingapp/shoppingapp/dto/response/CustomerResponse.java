package com.shoppingapp.shoppingapp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {

    Long id;

    String firstName;
    String lastName;

    String email;

    String phone;

    Boolean isActive;

    Long totalOrder;
}
