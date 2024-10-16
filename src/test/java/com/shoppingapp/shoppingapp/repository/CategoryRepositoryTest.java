package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
class CategoryRepositoryTest {


    @Mock
    private CategoryRepository categoryRepository;

    private User user;
    private Cart cart;
    private CartItem cartItem;
    private Product product;
    private Category category;

    @BeforeEach
    void initData() {
        MockitoAnnotations.openMocks(this); // Mở các mock

        // Khởi tạo dữ liệu người dùng, sản phẩm và giỏ hàng
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

        category = Category.builder()
                .categoryName("Electronics")
                .description("Electronics and gadgets")
                .isActive(true)
                .picture("Picture")
                .build();
    }

    @Test
    void existsByCategoryName_ReturnsTrue_WhenCategoryExists() {
         when(categoryRepository.existsByCategoryName(category.getCategoryName())).thenReturn(true);

        // Act
        boolean exists = categoryRepository.existsByCategoryName(category.getCategoryName());

        // Assert
        assertTrue(exists, "Expected category to exist");
    }

    @Test
    void existsByCategoryName_ReturnsFalse_WhenCategoryDoesNotExist() {
         when(categoryRepository.existsByCategoryName("NonExistingCategory")).thenReturn(false);

        // Act
        boolean exists = categoryRepository.existsByCategoryName("NonExistingCategory");

        // Assert
        assertFalse(exists, "Expected category to not exist");
    }
}
