package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.PaymentCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.PaymentUpdateRequest;
import com.shoppingapp.shoppingapp.models.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> getAllPayments();
    Payment getPayment(Long id);
    Payment addPayment(PaymentCreationRequest request);
    Payment updatePayment(PaymentUpdateRequest request, Long id);
    String deletePayment(Payment payment);
}
