package com.shoppingapp.shoppingapp.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentUpdateRequest {
    String paymentType;
    Boolean isActive;
}