package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
@SpringBootTest
class PaymentRepositoryTest {

    @Mock
    private PaymentRepository paymentRepository;
    Payment payment;
    @BeforeEach
    void initData(){
        MockitoAnnotations.openMocks(this);

        payment =Payment.builder()
                .paymentId(1L)
                .isActive(true)
                .paymentType("paymentType")
                .build();
    }
    @Test
    void existsByPaymentType_success() {
        when(paymentRepository.existsByPaymentType(payment.getPaymentType())).thenReturn(true);
        assertTrue(paymentRepository.existsByPaymentType("paymentType"),"Expect payment exists");
    }

    @Test
    void existsByPaymentType_fail() {
        when(paymentRepository.existsByPaymentType("NonExistingPayment")).thenReturn(false);
        assertFalse(paymentRepository.existsByPaymentType("NonExistingPayment"),"Expect payment not exists");
    }
}