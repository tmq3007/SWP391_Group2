package com.shoppingapp.shoppingapp.dto.request;

import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.models.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionCreationRequest {
    private Long user;
    private Long order;
    private Long shop;
    private LocalDateTime date;

}
