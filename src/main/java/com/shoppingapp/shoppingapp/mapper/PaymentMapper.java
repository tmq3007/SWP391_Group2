package com.shoppingapp.shoppingapp.mapper;

import com.shoppingapp.shoppingapp.dto.request.CategoryUpdateRequest;
import com.shoppingapp.shoppingapp.dto.request.PaymentCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.PaymentUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.PaymentResponse;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.models.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toPayment(PaymentCreationRequest paymentCreationRequest);
    void updatePayment(@MappingTarget Payment payment, PaymentUpdateRequest paymentCreationRequest);
    PaymentResponse toPaymentResponse(Payment payment);
}
