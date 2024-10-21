package com.shoppingapp.shoppingapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.ResetPasswordRequest;
import com.shoppingapp.shoppingapp.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    private ResetPasswordRequest resetPasswordRequest;

    @BeforeEach
    void initData() {
        resetPasswordRequest = ResetPasswordRequest.builder()
                .email("test@example.com")
                .build();
    }

    @Test
    void resetPassword_validRequest_returnsSuccessResponse() throws Exception {
        // Given the service method is called and completes successfully
        Mockito.doNothing().when(emailService).resetPassword(ArgumentMatchers.any(ResetPasswordRequest.class));

        // Convert request to JSON string
        String content = new ObjectMapper().writeValueAsString(resetPasswordRequest);

        // Perform POST request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reset-password")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("Reset password successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0)); // Assuming code is included in ApiResponse
    }

    @Test
    void resetPassword_invalidEmail_returnsBadRequest() throws Exception {
        // Test with invalid email
        resetPasswordRequest.setEmail("invalid-email");

        // Convert request to JSON string
        String content = new ObjectMapper().writeValueAsString(resetPasswordRequest);

        // Assume the service method throws an exception for invalid email
        Mockito.doThrow(new RuntimeException("Invalid email")).when(emailService).resetPassword(ArgumentMatchers.any(ResetPasswordRequest.class));

        // Perform POST request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reset-password")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Uncategorized error"));
    }
}