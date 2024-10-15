package com.shoppingapp.shoppingapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingapp.shoppingapp.dto.request.UserCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.UserUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.UserResponse;
import com.shoppingapp.shoppingapp.service.UserService;
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


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserCreationRequest request;
    private UserUpdateRequest updateRequest;
    private UserResponse userResponse;


    private UserResponse userResponse1;
    private UserResponse userResponse2;
    private UserResponse updatedUserResponse;



    @BeforeEach
    void initData() {
        request = UserCreationRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("aro177@gmail.com")
                .username("johndoe")
                .password("password")
                .phone("08012345678")
                .build();

        userResponse = UserResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("aro177@gmail.com")
                .username("johndoe")
                .phone("08012345678")
                .build();

        userResponse1 = UserResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .username("johndoe")
                .phone("08012345678")
                .build();

        userResponse2 = UserResponse.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .username("janesmith")
                .phone("08087654321")
                .build();

        updateRequest = UserUpdateRequest.builder()
                .firstName("John Updated")
                .lastName("Doe Updated")
                .email("johnupdated@example.com")
                .username("johnupdated")
                .password("newpassword")
                .isActive(true)
                .build();

        updatedUserResponse = UserResponse.builder()
                .id(1L)
                .firstName("John Updated")
                .lastName("Doe Updated")
                .email("johnupdated@example.com")
                .username("johnupdated")
                .phone("08012345678")
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);

        //WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0)
        );

    }

    @WithMockUser // Mock user authentication for testing
    @Test
    void getAllUsers_success() throws Exception {
        //GIVEN
        List<UserResponse> users = Arrays.asList(userResponse1, userResponse2);
        Mockito.when(userService.getAll()).thenReturn(users);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result", org.hamcrest.Matchers.hasSize(2))) // Check the size of the response
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].firstName").value("John")) // Check the first user's first name
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].firstName").value("Jane")); // Check the second user's first name
    }

    @WithMockUser // Mock user authentication for testing
    @Test
    void updateUser_validRequest_success() throws Exception {
        // GIVEN
        Long userId = 1L;
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(updateRequest);


        Mockito.when(userService.updateUser(ArgumentMatchers.eq(userId), ArgumentMatchers.any(UserUpdateRequest.class)))
                .thenReturn(updatedUserResponse);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.firstName").value("John Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.lastName").value("Doe Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.email").value("johnupdated@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.username").value("johnupdated"));
    }

    @WithMockUser // Mock user authentication for testing
    @Test
    void deleteUser_validRequest_success() throws Exception {
        // GIVEN
        Long userId = 1L;


        Mockito.doNothing().when(userService).deleteUser(userId);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("User has been deleted"));
    }
}
