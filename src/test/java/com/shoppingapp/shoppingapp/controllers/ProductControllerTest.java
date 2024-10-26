package com.shoppingapp.shoppingapp.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingapp.shoppingapp.dto.request.ProductCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ProductUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ProductResponse;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.service.ProductService;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc

public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ProductCreationRequest request;
    private ProductCreationRequest request1;

    private Product productResponse;
    private Category category;
    private Shop shop;


    @BeforeEach
    void initData(){
        // Create dummy Category and Shop
        category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Electronics");

        shop = new Shop();

        shop.setShopId(1L);
        shop.setShopName("Shop1");


        request = ProductCreationRequest.builder()
                .productName("siu")
                .category(category.getCategoryId())
                .shop(shop.getShopId())
                .description("duoc")
                .measurementUnit("12")
                .unitBuyPrice(Double.valueOf("12"))
                .unitSellPrice(Double.valueOf("40"))
                .discount(Double.valueOf("0.2"))
                .stock(Integer.parseInt("20"))
                .isActive(Boolean.valueOf("true"))
                .build();
        productResponse = Product.builder()
                .productName("siu")
                .category(category)
                .shop(shop)
                .description("duoc")
                .measurementUnit("12")
                .unitBuyPrice(Double.valueOf("12"))
                .unitSellPrice(Double.valueOf("40"))
                .discount(Double.valueOf("0.2"))
                .stock(Integer.parseInt("20"))
                .pictureUrl("")
                .pictureUrl2("")
                .isActive(Boolean.valueOf("true"))
                .build();
    }

    @Test
    void createProduct_validRequest_success() throws Exception {
        //Given
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(productService.createProduct(ArgumentMatchers.any()))
                .thenReturn(productResponse);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("result.productName").value("siu"));


        //Then

    }

    @Test
    void createProduct_validRequest_fail() throws Exception {
        //Given
        request1 = ProductCreationRequest.builder()
                .productName("siu")
                .category(2L)
                .shop(shop.getShopId())
                .description("duoc")
                .measurementUnit("12")
                .unitBuyPrice(Double.valueOf("12"))
                .unitSellPrice(Double.valueOf("40"))
                .discount(Double.valueOf("0.2"))
                .stock(Integer.parseInt("20"))
                .isActive(Boolean.valueOf("true"))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        //When then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                ;
    }

    @Test
    void getAllProducts() throws Exception {
        // Given
        Product product1 = productResponse;
        Product product2 = Product.builder().productName("Product 2").category(category).shop(shop).build();

        List<Product> productList = Arrays.asList(product1, product2);
        Mockito.when(productService.getAllProducts()).thenReturn(productList);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].productName").value("siu"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].productName").value("Product 2"));
    }



    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN"  })
    void getProductById() throws Exception {
        // Given
        Mockito.when(productService.getProductById(ArgumentMatchers.anyLong())).thenReturn(productResponse);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("productName").value("siu"));
    }




    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN"  })
    void updateProduct() throws Exception {
        // Given
        ProductUpdateRequest updateRequest = ProductUpdateRequest.builder()
                .category(2L)
                .shop(1L)
                .description("Updated product description")
                .measurementUnit("kg")
                .unitBuyPrice(15.0)
                .unitSellPrice(50.0)
                .discount(0.1)
                .stock(100)
                .pictureUrl("new_image_url")
                .pictureUrl2("new_image_url_2")
                .isActive(true)
                .build();

        ProductResponse updatedProductResponse = ProductResponse.builder()
                .productName("Updated Product")
                .category(2L)
                .shop(1L)
                .description("Updated product description")
                .measurementUnit("kg")
                .unitBuyPrice(15.0)
                .unitSellPrice(50.0)
                .discount(0.1)
                .stock(100)
                .pictureUrl("new_image_url")
                .pictureUrl2("new_image_url_2")
                .isActive(true)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(updateRequest);

        Mockito.when(productService.updateProduct(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
                .thenReturn(updatedProductResponse);

        // When
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("productName").value("Updated Product"));
    }

    @Test
    @WithMockUser
    void deleteProduct() throws Exception {
            // Given
            Mockito.when(productService.deleteProductById(ArgumentMatchers.any())).thenReturn("Product deleted successfully");

            // When
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
