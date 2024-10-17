package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest // Use DataJpaTest for repository testing
class CartItemRepositoryTest {

    @MockBean
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
    void findByCartAndProductAndBuyUnit_ReturnsCartItem_WhenExists() {
        // Arrange
        when(cartItemRepository.findByCartAndProductAndBuyUnit(cart, product, "piece")).thenReturn(cartItem);

        // Act
        CartItem foundCartItem = cartItemRepository.findByCartAndProductAndBuyUnit(cart, product, "piece");

        // Assert
        assertNotNull(foundCartItem, "Expected cart item to be present");
        assertEquals(cartItem.getId(), foundCartItem.getId(), "Expected the found cart item ID to match the saved cart item ID");
        assertEquals(product.getProductId(), foundCartItem.getProduct().getProductId(), "Expected product ID to match");
    }

    @Test
    void findByCartAndProductAndBuyUnit_ReturnsNull_WhenDoesNotExist() {
        // Arrange
        when(cartItemRepository.findByCartAndProductAndBuyUnit(cart, product, "non-existing-unit")).thenReturn(null);

        // Act
        CartItem foundCartItem = cartItemRepository.findByCartAndProductAndBuyUnit(cart, product, "non-existing-unit");

        // Assert
        assertNull(foundCartItem, "Expected cart item to be null for non-existing unit");
    }
}
