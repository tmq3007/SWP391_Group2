package com.shoppingapp.shoppingapp.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.service.ShopService;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
public class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ShopService shopService;

    ShopCreationRequest request;
    ShopCreationRequest request1;

    ShopResponse shopResponse;

    @BeforeEach
    void initData(){
        User user = new User() ;
        user.setId(1L);

        request = new ShopCreationRequest().builder()
                .shopName("Pet Shops")
                .logo("logo.img")
                .cover("cover.img")
                .address("Hoa Lac")
                .user(1L)
                .city("Ha Noi")
                .district("Thach That")
                .subdistrict("Thach Hoa")
                .description("Pet Shop sell pet things")
                .phone("0123456789")
                .build();

        request1 = ShopCreationRequest.builder()
                .shopName("Pet Shops")
                .logo("logo.img")
                .cover("cover.img")
                .address("Hoa Lac")
                .user(1L)
                .city("Ha Noi")
                .district("Thach That")
                .subdistrict("Thach Hoa")
                .description("Pet Shop sell pet things")
                .phone("0123456789")
                .build();

        shopResponse = new ShopResponse().builder()
                .shopId(1L)
                .shopName("Pet Shops")
                .logo("logo.img")
                .cover("cover.img")
                .address("Hoa Lac")
                .user(user)
                .city("Ha Noi")
                .district("Thach That")
                .subdistrict("Thach Hoa")
                .description("Pet Shop sell pet things")
                .phone("0123456789")
                .build();
    }
    ShopUpdateRequest updateRequest = ShopUpdateRequest.builder()
            .shopName("Updated Shop Name")
            .address("Updated Address")
            .city("Updated City")
            .district("Updated District")
            .subdistrict("Updated Subdistrict")
            .phone("0987654321")
            .description("Updated Description")
            .logo("updated_logo.png")
            .cover("updated_cover.png")
            .build();

    ShopResponse updatedShopResponse = ShopResponse.builder()
            .shopId(1L)
            .shopName("Updated Shop Name")
            .address("Updated Address")
            .city("Updated City")
            .district("Updated District")
            .subdistrict("Updated Subdistrict")
            .phone("0987654321")
            .description("Updated Description")
            .logo("updated_logo.png")
            .cover("updated_cover.png")
            .build();

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN"  })
    void createShop_validRequest_success() throws Exception {
        //given
        ObjectMapper oj = new ObjectMapper();
        oj.registerModule(new JavaTimeModule());
        String content = oj.writeValueAsString(request);

        Mockito.when(shopService.createShop(ArgumentMatchers.any()))
                .thenReturn(shopResponse);
        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/shops")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0));
        //then

    }


    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    void getAllShops_success() throws Exception {
        List<ShopResponse> shopList = Arrays.asList(shopResponse);
        Mockito.when(shopService.getAllShops()).thenReturn(shopList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shops")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].shopName").value("Pet Shops"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    void getShopById_success() throws Exception {
        Mockito.when(shopService.getShopById(1L)).thenReturn(shopResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shops/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.shopName").value("Pet Shops"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    void deleteShop() throws Exception {
        // Given
        Mockito.when(shopService.deleteShop(ArgumentMatchers.anyLong())).thenReturn("Shop is deleted");

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/shops/{shopId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("Shop is deleted"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    void updateShop_success() throws Exception {
        // Given
        ObjectMapper objectMapper = new ObjectMapper();

        Mockito.when(shopService.updateShop(ArgumentMatchers.any(ShopUpdateRequest.class), ArgumentMatchers.eq(1L)))
                .thenReturn(updatedShopResponse);

        String content = objectMapper.writeValueAsString(updateRequest);

        // When
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/shops/{shopId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shopName").value("Updated Shop Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Updated Address"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Updated City"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("0987654321"));
    }
    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    void getTotalShops_success() throws Exception {
        Mockito.when(shopService.getTotalShops()).thenReturn(5);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shops/total-shops")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(5));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    void createShop_shopNameExit_fail() throws Exception {
        // Given


        // Convert the request object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request1);

        // Mock the service to throw an AppException when the shop name already exists
        Mockito.when(shopService.createShop(ArgumentMatchers.any()))
                .thenThrow(new AppException(ErrorCode.SHOP_EXISTED)); // Adjust the error code accordingly

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/shops")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1013)) // Adjust this code based on your error handling
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Shop existed")); // Adjust the message accordingly
    }










}
