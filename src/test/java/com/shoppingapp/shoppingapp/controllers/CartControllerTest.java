package com.shoppingapp.shoppingapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingapp.shoppingapp.dto.request.AddItemRequest;
import com.shoppingapp.shoppingapp.dto.response.UserResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.models.*;
import com.shoppingapp.shoppingapp.service.CartItemService;
import com.shoppingapp.shoppingapp.service.CartService;
import com.shoppingapp.shoppingapp.service.ProductService;
import com.shoppingapp.shoppingapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private CartService cartService;

    @MockBean
    private CartItemService cartItemService;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserService userService;

    private User user;
    private Cart cart;
    private UserResponse userResponse;
    private CartItem cartItem;
    private Product product;
    private AddItemRequest addItemRequest;

    @BeforeEach
    void initData() {


        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("aro177@gmail.com")
                .username("johndoe")
                .phone("08012345678")
                .roles(Set.of(Role.builder()
                        .name("CUSTOMER")
                        .description("Customer Role")
                        .build()))
                .build();

        product = Product.builder()
                .productId(1L)
                .productName("Sample Product")
                .unitBuyPrice(50.0)
                .description("Sample product description")
                .measurementUnit("2")
                .stock(100)
                .build();
        addItemRequest = AddItemRequest.builder()
                .productId(product.getProductId())
                .buyUnit(product.getMeasurementUnit())
                .quantity(3)
                .build();
        cartItem = CartItem.builder()
                .id(1L)
                .product(product)
                .quantity(2)
                .totalPrice(100.0)
                .buyUnit("piece")
                .userId(user.getId())
                .build();

        cart = Cart.builder()
                .id(1L)
                .user(user)
                .totalItem(1)
                .cartItems(Set.of(cartItem))
                .totalPrice(100.0)
                .build();
        userResponse = UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "CUSTOMER", "VENDOR"})
    void findUserCartHandler_success() throws Exception {


        when(userService.getUserById(1L)).thenReturn(userResponse)
                .thenThrow(new AppException(ErrorCode.USER_NOT_EXISTED));
        when(cartService.findUserCart(any(User.class))).thenReturn(cart)
                .thenThrow(new AppException(ErrorCode.CART_NOT_EXIST));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cart/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.totalItem").value(cart.getTotalItem()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.totalPrice").value(cart.getTotalPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.cartItems[0].id").value(cartItem.getId()))
        ;
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "CUSTOMER", "VENDOR"})
    void addItemToCart_request_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(addItemRequest);

        when(userService.getUserById(1L))
                .thenReturn(userResponse)
                .thenThrow(AppException.class);
        when(productService.getProductById(addItemRequest.getProductId()))
                .thenReturn(product)
                .thenThrow(AppException.class);
        when(cartService.addCartItem(any(User.class), any(Product.class), any(String.class), any(Integer.class)))
                .thenReturn(cartItem)
                .thenThrow(AppException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cart/add/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value(cartItem.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.buyUnit").value(cartItem.getBuyUnit()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.quantity").value(cartItem.getQuantity())); // Adjust the expectation here
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "CUSTOMER", "VENDOR"})
    void deleteCart_request_success() throws Exception {
        when(userService.getUserById(1L)).thenReturn(userResponse);
        doNothing().when(cartItemService).removeCartItem(user.getId(), cartItem.getId());


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cart/delete/user/1/cartItem/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("delete item from cart success"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "CUSTOMER", "VENDOR"})
    void updateCartItemHandler_request_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(cartItem); // Assuming you want to update the existing cartItem

        when(userService.getUserById(1L)).thenReturn(userResponse);
        when(cartItemService.updateCartItem(any(Long.class), any(Long.class), any(CartItem.class)))
                .thenReturn(cartItem); // Return the updated cartItem

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cart/delete/user/1/cartItem/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value(cartItem.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.quantity").value(cartItem.getQuantity()));
    }

}
