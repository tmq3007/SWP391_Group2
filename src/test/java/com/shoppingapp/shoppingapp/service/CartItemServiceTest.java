package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.models.*;
import com.shoppingapp.shoppingapp.repository.CartItemRepository;
import com.shoppingapp.shoppingapp.service.Impl.CartItemServiceImpl; // Ensure you're importing the correct implementation
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks; // Use InjectMocks instead of Autowired
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartItemServiceTest {

    @InjectMocks // Use InjectMocks for the implementation
    private CartItemServiceImpl cartItemService; // Change to the implementation class

    @Mock
    private CartItemRepository cartItemRepository;

    private User user;
    private Cart cart;
    private CartItem cartItem;
    private Product product;
    private CartItem updatedCartItem;

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

        updatedCartItem = CartItem.builder()
                .id(1L)
                .product(product)
                .quantity(5)
                .totalPrice(product.getUnitSellPrice() * 5)
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
    void updateCartItem_Success() throws Exception {
        // Arrange
        when(cartItemRepository.findById(cartItem.getId())).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        // Act
        CartItem result = cartItemService.updateCartItem(user.getId(), cartItem.getId(), updatedCartItem);

        // Assert
        assertNotNull(result);
        assertEquals(5, result.getQuantity());
        assertEquals(275.0, result.getTotalPrice()); // 5 * 55.0
        //verify(cartItemRepository, times(1)).save(cartItem);
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void removeCartItem_Success() throws Exception {
        // Arrange
        when(cartItemRepository.findById(cartItem.getId())).thenReturn(Optional.of(cartItem));

        // Act
        cartItemService.removeCartItem(user.getId(), cartItem.getId());

        // Assert
        verify(cartItemRepository, times(1)).delete(cartItem);
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void findCartItemById_Success() throws Exception {
        // Arrange
        when(cartItemRepository.findById(cartItem.getId())).thenReturn(Optional.of(cartItem));

        // Act
        CartItem result = cartItemService.findCartItemById(cartItem.getId());

        // Assert
        assertNotNull(result);
        assertEquals(cartItem.getId(), result.getId());
        verify(cartItemRepository, times(1)).findById(cartItem.getId());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void updateCartItem_NotFound() throws Exception {
        // Arrange
        when(cartItemRepository.findById(cartItem.getId())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(AppException.class, () -> {
            cartItemService.updateCartItem(user.getId(), cartItem.getId(), updatedCartItem);
        });

        assertEquals("Cart not existed", exception.getMessage());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void removeCartItem_NotFound() throws Exception {
        // Arrange
        when(cartItemRepository.findById(cartItem.getId())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(AppException.class, () -> {
            cartItemService.removeCartItem(user.getId(), cartItem.getId());
        });

        assertEquals("Cart not existed"  , exception.getMessage());
    }
}
