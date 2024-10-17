package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.PaymentCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.PaymentUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.PaymentResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.models.Payment;
import com.shoppingapp.shoppingapp.repository.PaymentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @MockBean
    private PaymentRepository paymentRepository;
    private PaymentCreationRequest requestCreate;
    private PaymentUpdateRequest requestUpdate;
    private PaymentResponse response;
    private PaymentResponse responseUpdate;

    private Payment payment;
    private Payment payment2;
    @BeforeEach
    void initData(){
        requestCreate = PaymentCreationRequest.builder()

                .paymentType("paymentType")
                .isActive(true)
                .build();
        requestUpdate = PaymentUpdateRequest.builder()
                .paymentType("paymentType Update")
                .isActive(true)
                .build();
        response = PaymentResponse.builder()
                .paymentType("paymentType")
                .isActive(true)
                .paymentId(1L)
                .build();
        responseUpdate = PaymentResponse.builder()
                .paymentType("paymentType Update")
                .isActive(true)
                .build();
        payment = Payment.builder()
                .paymentId(1L)
                .paymentType("paymentType")
                .isActive(true)
                .build();
        payment2 = Payment.builder()
                .paymentId(1L)
                .paymentType("paymentType Update")
                .isActive(true)
                .build();
    }

    @Test
    void getAllPayments_success() {
        List<Payment> payments = Arrays.asList(payment);
        Mockito.when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0).getPaymentType()).isEqualTo("paymentType");
    }

    @Test
    void getPayment_success() {
        Mockito.when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        Payment result = paymentService.getPaymentById(1L);
        Assertions.assertThat(result.getPaymentId()).isEqualTo(1);
        Assertions.assertThat(result.getPaymentType()).isEqualTo("paymentType");
    }

    @Test
    void getPayment_paymentNotFound_fail() {
        // Simulate payment not being found by returning Optional.empty()
        Mockito.when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Assert that the exception is thrown with the correct error code
        Assertions.assertThatThrownBy(() -> paymentService.getPaymentById(1L))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(ErrorCode.PAYMENT_NOT_FOUND.getMessage());
    }


    @Test
    void getPaymentById() {
        Mockito.when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        Payment result = paymentService.getPaymentById(1L);
        Assertions.assertThat(result.getPaymentId()).isEqualTo(1);
        Assertions.assertThat(result.getPaymentType()).isEqualTo("paymentType");
    }
    @Test
    void getPaymentById_paymentNotFound_fail() {
        // Simulate payment not being found by returning Optional.empty()
        Mockito.when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Assert that the exception is thrown with the correct error code
        Assertions.assertThatThrownBy(() -> paymentService.getPaymentById(1L))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(ErrorCode.PAYMENT_NOT_FOUND.getMessage());
    }

    @Test
    void addPayment_success() {
        Mockito.when(paymentRepository.existsById(anyLong())).thenReturn(false);
        Mockito.when(paymentRepository.save(ArgumentMatchers.any(Payment.class))).thenReturn(payment);
        var result = paymentService.addPayment(requestCreate);
        Assertions.assertThat(result.getPaymentId()).isEqualTo(1L);
        Assertions.assertThat(result.getPaymentType()).isEqualTo("paymentType");
    }

    @Test
    void addPayment_paymentExist_fail() {
        // Simulate that the payment already exists
        Mockito.when(paymentRepository.existsByPaymentType(anyString())).thenReturn(true);

        Assertions.assertThatThrownBy(() -> paymentService.addPayment(requestCreate))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(ErrorCode.PAYMENT_EXISTED.getMessage());
    }



    @Test
    void updatePayment_success() {
        Mockito.when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));
        Mockito.when(paymentRepository.save(ArgumentMatchers.any(Payment.class))).thenReturn(payment2);
        var result = paymentService.updatePayment(requestUpdate,1L);
        Assertions.assertThat(result.getPaymentId()).isEqualTo(1L);
        Assertions.assertThat(result.getPaymentType()).isEqualTo("paymentType Update");
    }

    @Test
    void updatePayment_paymentNotFound_fail() {
        // Simulate that the payment is not found by returning Optional.empty()
        Mockito.when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Assert that an AppException is thrown when the payment is not found
        Assertions.assertThatThrownBy(() -> paymentService.updatePayment(requestUpdate, 1L))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(ErrorCode.PAYMENT_NOT_FOUND.getMessage());
    }


    @Test
    void deletePayment_success() {
        Mockito.when(paymentRepository.existsById(anyLong())).thenReturn(true); // Ensure category exists

        Mockito.when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));
        Mockito.doNothing().when(paymentRepository).deleteById(anyLong());
        String result = paymentService.deletePayment(payment);
        Assertions.assertThat(result).isEqualTo("Payment deleted with id:"+1);
    }

    @Test
    void deletePayment_paymentNotFound_fail() {
        // Simulate that the payment is not found by returning Optional.empty()
        Mockito.when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Expect an exception to be thrown when trying to delete a non-existing payment
        Assertions.assertThatThrownBy(() -> paymentService.deletePayment(payment))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(ErrorCode.PAYMENT_NOT_FOUND.getMessage());
    }

}