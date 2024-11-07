package com.shoppingapp.shoppingapp.dto.response;

import com.shoppingapp.shoppingapp.models.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersResponse {
    Long orderId;
    User user;
    LocalDate paymentDate;
    LocalDate orderDate;
    Boolean isPaid;
}
