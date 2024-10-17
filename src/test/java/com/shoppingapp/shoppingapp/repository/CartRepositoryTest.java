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
class CartRepositoryTest {

    @MockBean
    private CartRepository cartRepository;
    @MockBean
    private UserRepository userRepository;

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
    void findByUserId_ReturnsCart_WhenCartExists() {
        // Act
        when(cartRepository.findByUserId(user.getId())).thenReturn(cart);

        Cart foundCart = cartRepository.findByUserId(user.getId());

        // Assert
         assertNotNull(foundCart, "Expected cart to be present for user ID " + user.getId());
        assertEquals(cart.getId(), foundCart.getId(), "Expected the found cart ID to match the saved cart ID");
        assertEquals(user.getId(), foundCart.getUser().getId(), "Expected user ID to match");
    }

    @Test
    void findByUserId_ReturnsNull_WhenCartDoesNotExist() {
        // Act
        Cart foundCart = cartRepository.findByUserId(999L); // Use a non-existing user ID

        // Assert
        assertNull(foundCart, "Expected cart to be null for non-existing user ID");
    }
}
