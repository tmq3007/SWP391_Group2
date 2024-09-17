package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.models.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> getAllPayments();
    Payment getPayment(Long id);
    Payment addPayment(Payment payment);
    Payment updatePayment(Payment payment);
    String deletePayment(Payment payment);
}
