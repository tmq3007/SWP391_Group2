package com.shoppingapp.shoppingapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingapp.shoppingapp.dto.request.AuthenticationRequest;
import com.shoppingapp.shoppingapp.dto.request.IntrospectRequest;
import com.shoppingapp.shoppingapp.dto.request.LogoutRequest;
import com.shoppingapp.shoppingapp.dto.request.RefreshRequest;
import com.shoppingapp.shoppingapp.dto.response.AuthenticationResponse;
import com.shoppingapp.shoppingapp.dto.response.IntrospectResponse;
import com.shoppingapp.shoppingapp.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    private AuthenticationRequest authenticationRequest;
    private AuthenticationResponse authenticationResponse;
    private IntrospectRequest introspectRequest;
    private IntrospectResponse introspectResponse;

    private LogoutRequest logoutRequest;

    private RefreshRequest refreshRequest;


    @BeforeEach
    void initData() {
        authenticationRequest = AuthenticationRequest.builder()
                .username("johndoe")
                .password("password")
                .build();

        authenticationResponse = AuthenticationResponse.builder()
                .token("sample-jwt-token")
                .build();

        introspectRequest = IntrospectRequest.builder()
                .token("sampleToken")
                .authenticated(true)
                .build();

        introspectResponse = IntrospectResponse.builder()
                .valid(true)
                .build();

        logoutRequest = LogoutRequest.builder()
                .token("sampleToken")
                .build();

        refreshRequest = RefreshRequest.builder()
                .token("invalidToken")
                .build();


    }

    @Test
    void login_validCredentials_success() throws Exception {
        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(authenticationRequest);

        when(authenticationService.isAuthenticated(ArgumentMatchers.any(AuthenticationRequest.class)))
                .thenReturn(authenticationResponse);

        //WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.token").value("sample-jwt-token"));
    }

    @Test
    void isAuthenticated_validToken_returnsValidResponse() throws Exception {
        // Mocking the service response for a valid token
        when(authenticationService.introspect(ArgumentMatchers.any(IntrospectRequest.class)))
                .thenReturn(introspectResponse);

        // Convert request to JSON string
        String content = new ObjectMapper().writeValueAsString(introspectRequest);

        // Perform POST request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/introspect")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.valid").value(true));
    }

    @Test
    void isAuthenticated_invalidToken_returnsInvalidResponse() throws Exception {
        // Mocking the service response for an invalid token
        IntrospectResponse invalidResponse = IntrospectResponse.builder()
                .valid(false)
                .build();

        when(authenticationService.introspect(ArgumentMatchers.any(IntrospectRequest.class)))
                .thenReturn(invalidResponse);

        // Convert request to JSON string
        String content = new ObjectMapper().writeValueAsString(introspectRequest);

        // Perform POST request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/introspect")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.valid").value(false));
    }

    @Test
    void logout_validRequest_returnsOkStatus() throws Exception {
        // No need to mock a return value because logout returns void.
        Mockito.doNothing().when(authenticationService).logout(Mockito.any(LogoutRequest.class));

        // Convert request to JSON string
        String content = new ObjectMapper().writeValueAsString(logoutRequest);

        // Perform POST request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0));
    }

    @Test
    void refreshToken_validToken_returnsNewToken() throws Exception {
        // Create a valid AuthenticationResponse
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token("new-valid-jwt-token")
                .isAuthenticated(true)
                .build();

        // Mock the behavior of authenticationService to return a valid AuthenticationResponse
        Mockito.when(authenticationService.refreshToken(Mockito.any(RefreshRequest.class)))
                .thenReturn(authenticationResponse);

        // Convert request to JSON string
        String content = new ObjectMapper().writeValueAsString(refreshRequest);

        // Perform POST request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.token").value("new-valid-jwt-token"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.authenticated").value(true));
    }



}
