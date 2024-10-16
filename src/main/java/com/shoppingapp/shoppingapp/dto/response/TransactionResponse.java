package com.shoppingapp.shoppingapp.dto.response;

import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.models.User;

import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class TransactionResponse {

    private User user;
    private Orders order;
    private Shop shop;
    private LocalDateTime date;
}
