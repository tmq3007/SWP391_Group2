package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.AddItemRequest;
import com.shoppingapp.shoppingapp.dto.response.UserResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.models.*;
import com.shoppingapp.shoppingapp.repository.CartItemRepository;
import com.shoppingapp.shoppingapp.repository.CartRepository;
import com.shoppingapp.shoppingapp.service.Impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Mock
    private ProductService productService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    private User user;
    private Cart cart;
    private CartItem cartItem;
    private Product product;

    @BeforeEach
    void initData() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("aro177@gmail.com")
                .username("johndoe")
                .phone("08012345678")
                .isActive(true)
                .roles(Set.of(Role.builder()
                        .name("CUSTOMER")
                        .description("Customer Role")
                        .build()))
                .build();

        product = Product.builder()
                .productId(1L)
                .productName("Sample Product")
                .unitBuyPrice(50.0)
                .unitSellPrice(55.0)
                .description("Sample product description")
                .measurementUnit("piece")
                .stock(100)
                .build();

        cartItem = CartItem.builder()
                .id(1L)
                .product(product)
                .quantity(3)
                .totalPrice(product.getUnitSellPrice() * 3)
                .buyUnit("piece")
                .userId(user.getId())
                .build();

        cart = Cart.builder()
                .id(1L)
                .user(user)
                .totalItem(1)
                .cartItems(Set.of(cartItem))
                .totalPrice(product.getUnitSellPrice() * cartItem.getQuantity())
                .build();
        cartItem.setCart(cart);
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    @Transactional
    void addCartItem_request_success() throws Exception {
        // Arrange
        when(productService.getProductById(anyLong())).thenReturn(product);
        when(cartRepository.findByUserId(user.getId())).thenReturn(cart);
        when(cartItemRepository.findByCartAndProductAndBuyUnit(any(Cart.class), any(Product.class), anyString())).thenReturn(null);
        when(cartItemRepository.save(any(CartItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        CartItem addedItem = cartService.addCartItem(user, product, product.getMeasurementUnit(), 3);

        // Assert
        assertThat(addedItem).isNotNull();
        assertThat(addedItem.getProduct().getProductId()).isEqualTo(product.getProductId());
        assertThat(addedItem.getQuantity()).isEqualTo(3); // Ensure the quantity is set correctly
        assertThat(cart.getTotalItem()).isEqualTo(1); // Total items in cart after addition
        assertThat(cart.getTotalPrice()).isEqualTo(165.0); // Verify total price after addition

    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    @Transactional
    void addCartItem_invalid_quantity() {
        // Arrange
        int invalidQuantity = 0; // or any negative number
        when(cartRepository.findByUserId(anyLong())).thenReturn(cart);

        // Act & Assert
        assertThrows(AppException.class, () -> {
            cartService.addCartItem(user, product, product.getMeasurementUnit(), invalidQuantity);
        });
    }



    @Test
    @WithMockUser(roles = "CUSTOMER")
    @Transactional
    void findUserCart() throws Exception {
        // Arrange
        doReturn(cart).when(cartRepository).findByUserId(user.getId());


        // Act
        Cart foundCart = (cartRepository.findByUserId(user.getId()));

        System.out.println("Total Items in Cart: " + foundCart.getTotalItem());
        System.out.println("Total Price in Cart: " + foundCart.getTotalPrice());
        // Assert using AssertJ
        assertThat(foundCart).isNotNull(); // Ensure the cart is not null
        assertThat(foundCart.getUser().getId()).isEqualTo(user.getId());
        assertThat(foundCart.getTotalItem()).isEqualTo(cart.getTotalItem());
        assertThat(foundCart.getTotalPrice()).isEqualTo(cart.getTotalPrice());
    }
}
