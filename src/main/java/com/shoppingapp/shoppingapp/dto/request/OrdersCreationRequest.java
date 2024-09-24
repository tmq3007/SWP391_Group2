package com.shoppingapp.shoppingapp.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersCreationRequest {
    Long userId;
    Long paymentId;
    LocalDate paymentDate;
    LocalDate orderDate;
    Boolean isPaid;
}
