package com.shoppingapp.shoppingapp.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersUpdateRequest {
    LocalDate paymentDate;
    LocalDate orderDate;
    Boolean isPaid;
}
