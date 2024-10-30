package com.shoppingapp.shoppingapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingapp.shoppingapp.dto.request.UserCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.UserUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.CustomerResponse;
import com.shoppingapp.shoppingapp.dto.response.UserResponse;
import com.shoppingapp.shoppingapp.dto.response.VendorResponse;
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

    private UserResponse userProfileResponse;

    private Integer totalVendors;

    private Long userId;


    private List<VendorResponse> vendors;
    private List<CustomerResponse> customers;

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

        userProfileResponse = UserResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .username("johndoe")
                .phone("08012345678")
                .build();

        totalVendors = 10;

        userId = 1L;

        vendors = Arrays.asList(
                VendorResponse.builder().id(1L).email("vendor1@example.com").build(),
                VendorResponse.builder().id(2L).email("vendor2@example.com").build()
        );

        customers = Arrays.asList(
                CustomerResponse.builder().id(1L).email("customer1@example.com").build(),
                CustomerResponse.builder().id(2L).email("customer2@example.com").build()
        );
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

    @Test
    @WithMockUser(username = "johndoe") // Simulate an authenticated user
    void getMyInfo_validUser_success() throws Exception {
        // GIVEN
        Mockito.when(userService.getMyInfo()).thenReturn(userProfileResponse);

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/my-info")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.email").value("johndoe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.username").value("johndoe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.phone").value("08012345678"));
    }

    @Test
    @WithMockUser
    void getTotalVendors_validRequest_success() throws Exception {
        // GIVEN
        Mockito.when(userService.getTotalVendors()).thenReturn(totalVendors);

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/total-vendors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Status should be 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(totalVendors)); // Check if the result contains the total vendors
    }

    @Test
    @WithMockUser
    void banUser_validRequest_success() throws Exception {
        // Mock the service method for banning a user
        Mockito.doNothing().when(userService).banUser(userId);

        // Perform the PUT request for banning the user
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/ban/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Status should be 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("User has been banned")); // Check if the result contains the correct message
    }

    @Test
    @WithMockUser
    void unbanUser_validRequest_success() throws Exception {
        // Mock the service method for unbanning a user
        Mockito.doNothing().when(userService).unbanUser(userId);

        // Perform the PUT request for unbanning the user
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/unban/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Status should be 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("User has been unbanned")); // Check if the result contains the correct message
    }

    @Test
    @WithMockUser
    void getVendors_validRequest_success() throws Exception {
        // Mock the service method to return the vendors
        Mockito.when(userService.getVendors()).thenReturn(vendors);

        // Perform the GET request for vendors
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/all-vendors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Status should be 200 OK

                .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2)); // Ensure there are 2 vendors in the response
    }

    @Test
    @WithMockUser
    void getCustomers_validRequest_success() throws Exception {
        // Mock the service method to return the customers
        Mockito.when(userService.getCustomers()).thenReturn(customers);

        // Perform the GET request for customers
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/all-customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Status should be 200 OK

                .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2)); // Ensure there are 2 customers in the response
    }


}
