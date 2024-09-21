package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.PaymentCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.PaymentUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.PaymentResponse;
import com.shoppingapp.shoppingapp.models.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> getAllPayments();
    PaymentResponse getPayment(Long id);
    Payment getPaymentById(Long id);
    Payment addPayment(PaymentCreationRequest request);
    PaymentResponse updatePayment(PaymentUpdateRequest request, Long id);
    String deletePayment(Payment payment);
}
