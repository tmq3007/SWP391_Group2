package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.PaymentCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.PaymentUpdateRequest;

import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.mapper.PaymentMapper;
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

    @Autowired
    private PaymentMapper paymentMapper;
    @Override
    public PaymentResponse getPayment(Long id) {
        return paymentMapper.toPaymentResponse(paymentRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PAYMENT_NOT_FOUND)));
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
    }

    @Override
    public Payment addPayment(PaymentCreationRequest request) {
        Payment payment = new Payment();
        if (paymentRepository.existsByPaymentType(request.getPaymentType())) {
            throw new AppException(ErrorCode.PAYMENT_EXISTED);
        }else{
            payment.setPaymentType(request.getPaymentType());
            payment.setIsActive(request.getIsActive());

            return paymentRepository.save(payment);
        }
    }

    @Override
    public PaymentResponse updatePayment(PaymentUpdateRequest request, Long id) {

        Payment payment =  paymentRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
       paymentMapper.updatePayment(payment, request);
        return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
    }

    @Override
    public String deletePayment(Payment payment) {
        // Check if the payment exists in the database
        if (!paymentRepository.existsById(payment.getPaymentId())) {
            throw new AppException(ErrorCode.PAYMENT_NOT_FOUND);
        }

        // Proceed to delete the payment if it exists
        paymentRepository.delete(payment);
        return "Payment deleted with id:" + payment.getPaymentId();
    }

}
