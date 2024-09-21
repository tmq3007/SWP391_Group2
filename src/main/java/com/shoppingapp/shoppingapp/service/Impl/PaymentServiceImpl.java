package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.PaymentCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.PaymentUpdateRequest;
import com.shoppingapp.shoppingapp.models.Payment;
import com.shoppingapp.shoppingapp.repository.PaymentRepository;
import com.shoppingapp.shoppingapp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAllPayments() {
        return (List<Payment>) paymentRepository.findAll();
    }

    @Override
    public Payment getPayment(Long id) {
        return paymentRepository.findById(id).get();
    }

    @Override
    public Payment addPayment(PaymentCreationRequest request) {
        Payment payment = new Payment();
        payment.setPaymentType(request.getPaymentType());
        payment.setIsActive(request.getIsActive());

        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(PaymentUpdateRequest request, Long id) {

        Payment payment =  getPayment(id);
        payment.setPaymentType(request.getPaymentType());
        payment.setIsActive(request.getIsActive());
        return paymentRepository.save(payment);
    }

    @Override
    public String deletePayment(Payment payment) {
        paymentRepository.delete(payment);
        return "Payment deleted with id:" + payment.getPaymentId();
    }
}
