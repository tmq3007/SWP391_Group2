package com.shoppingapp.shoppingapp.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
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
@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
public class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ShopService shopService;
    ShopCreationRequest request;
    ShopResponse shopResponse;

    @BeforeEach
    void initData(){
        User user = new User() ;
        user.setId(1L);

        request = new ShopCreationRequest().builder()
                .shopName("Pet Shop")
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
                .shopID(1L)
                .shopName("Pet Shop")
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
                .post("/api/v1/shops/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0));
        //then

    }

}
