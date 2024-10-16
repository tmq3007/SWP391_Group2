package com.shoppingapp.shoppingapp.controllers;


import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.service.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;
    private ShopCreationRequest request;
    private ShopResponse response;

    @BeforeEach
    void initData(){
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

        response = new ShopResponse().builder()

                .build();
    }

    @Test
    void createShop(){

    }

}
