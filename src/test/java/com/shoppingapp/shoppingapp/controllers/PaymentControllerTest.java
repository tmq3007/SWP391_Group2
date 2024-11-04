package com.shoppingapp.shoppingapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingapp.shoppingapp.dto.request.*;
import com.shoppingapp.shoppingapp.dto.response.PaymentResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.models.Payment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PaymentService paymentService;

    private PaymentCreationRequest requestCreate;
    private PaymentUpdateRequest requestUpdate;
    private PaymentResponse response;
    private PaymentResponse responseUpdate;

    private Payment payment;
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
    }
    @Test
    @WithMockUser(username = "admin", authorities = { "CUSTOMER"  })
    void getAllPayments_success() throws Exception {
        List<Payment> payments = Arrays.asList(payment);
        ApiResponse<List<Payment>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(payments);

        Mockito.when(paymentService.getAllPayments())
                .thenReturn(payments);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].paymentType").value("paymentType"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].paymentId").value(1));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "CUSTOMER"  })
    void getPayment_validRequest_success() throws Exception {
        Long paymentId = 1L;
        Mockito.when(paymentService.getPayment(paymentId))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/payments/{paymentId}", paymentId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.paymentType").value("paymentType"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.paymentId").value(1));
    }
    @Test
    @WithMockUser(username = "admin", authorities = { "CUSTOMER"  })
    void getPayment_paymentExist_fail() throws Exception {
        Long paymentId = 1L;
        Mockito.when(paymentService.getPayment(paymentId))
                .thenThrow(new AppException(ErrorCode.PAYMENT_NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/payments/{paymentId}", paymentId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(4000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Payment not found"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "CUSTOMER"  })
    void createPayment_paymentExist_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(requestCreate);

        Mockito.when(paymentService.addPayment(ArgumentMatchers.any()))
                .thenReturn(payment);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.paymentType").value("paymentType"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.isActive").value("true"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "CUSTOMER"  })
    void createPayment_paymentExist_fail() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(requestCreate);

        Mockito.when(paymentService.addPayment(ArgumentMatchers.any()))
                .thenThrow(new AppException(ErrorCode.PAYMENT_EXISTED));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(4001))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Payment existed"));
    }


    @Test
    @WithMockUser(username = "admin", authorities = { "CUSTOMER"  })
    void updatePayment_validRequest_success() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(requestUpdate);

        Mockito.when(paymentService.updatePayment(ArgumentMatchers.any(), ArgumentMatchers.anyLong()))
                .thenReturn(responseUpdate);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/payments/{paymentId}",1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.paymentType").value("paymentType Update"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.isActive").value("true"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "CUSTOMER"  })
    void updatePayment_paymentNotExist_fail() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(requestUpdate);

        Mockito.when(paymentService.updatePayment(ArgumentMatchers.any(), ArgumentMatchers.anyLong()))
                .thenThrow(new AppException(ErrorCode.PAYMENT_NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/payments/{paymentId}",1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(4000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Payment not found"));
    }
    @Test
    @WithMockUser(username = "admin", authorities = { "CUSTOMER"  })
    void deletePayment_validRequest_success() throws Exception {
        Mockito.when(paymentService.getPaymentById(1L)).thenReturn(payment);

        Mockito.when(paymentService.deletePayment(ArgumentMatchers.any()))
                .thenReturn("Deleted Payment Successfully");

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/payments/{paymentId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("Deleted Payment Successfully"));
    }
    @Test
    @WithMockUser(username = "admin", authorities = { "CUSTOMER"  })
    void deletePayment_paymentNotExist_fail() throws Exception {
        Mockito.when(paymentService.getPaymentById(1L)).thenReturn(null);



        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/payments/{paymentId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(4000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Payment not found"));
    }
}