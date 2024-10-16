package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.ProductCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ProductUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ProductResponse;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    private ProductCreationRequest requestCreate;
    private ProductUpdateRequest requestUpdate;
    private Product product;
    private ProductResponse response;
    private Category category;
    private Shop shop;

    @BeforeEach
    void initData() {
        // Create dummy Category and Shop
        category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Electronics");

        shop = new Shop();
        shop.setShopId(1L);
        shop.setShopName("Shop1");

        // Initialize ProductCreationRequest for test cases
        requestCreate = ProductCreationRequest.builder()
                .category(1L)  // Chuyển thành Long ID thay vì đối tượng
                .shop(1L)      // Chuyển thành Long ID thay vì đối tượng
                .productName("TestProduct")
                .description("Test Product Description")
                .measurementUnit("12")
                .unitBuyPrice(50.0)
                .unitSellPrice(100.0)
                .discount(10.0)
                .stock(50)
                .pictureUrl("url1")
                .pictureUrl2("url2")
                .isActive(true)
                .build();

        // Initialize ProductUpdateRequest for test cases
        requestUpdate = ProductUpdateRequest.builder()
                .description("Updated Product Description")
                .measurementUnit("12")
                .unitBuyPrice(60.0)
                .unitSellPrice(120.0)
                .discount(15.0)
                .stock(100)
                .pictureUrl("updated_url1")
                .pictureUrl2("updated_url2")
                .isActive(true)
                .build();

        // Initialize the Product entity
        product = Product.builder()
                .productId(1L)
                .productName("TestProduct")
                .category(category)
                .shop(shop)
                .description("Test Product Description")
                .measurementUnit("12")
                .unitBuyPrice(50.0)
                .unitSellPrice(100.0)
                .discount(10.0)
                .stock(50)
                .pictureUrl("url1")
                .pictureUrl2("url2")
                .isActive(true)
                .build();

        // Initialize ProductResponse for test cases
        response = ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .measurementUnit(product.getMeasurementUnit())
                .unitBuyPrice(product.getUnitBuyPrice())
                .unitSellPrice(product.getUnitSellPrice())
                .discount(product.getDiscount())
                .stock(product.getStock())
                .pictureUrl(product.getPictureUrl())
                .pictureUrl2(product.getPictureUrl2())
                .isActive(product.getIsActive())
                .category(product.getCategory().getCategoryId())  // Sử dụng ID thay vì đối tượng
                .shop(product.getShop().getShopId())              // Sử dụng ID thay vì đối tượng
                .build();
    }

    @Test
    void getAllProducts() {
        // Mock the findAll method to return a list of products
        List<Product> products = Arrays.asList(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        // When
        List<Product> result = productService.getAllProducts();

        // Then
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0).getProductName()).isEqualTo("TestProduct");
    }

    @Test
    void getProductById() {
        // Mock the findById method
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // When
        Product result = productService.getProductById(1L);

        // Then
        Assertions.assertThat(result.getProductId()).isEqualTo(1L);
        Assertions.assertThat(result.getProductName()).isEqualTo("TestProduct");
    }

    @Test
    void createProduct() {
        // Mock save method
        Mockito.when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(product);

        // When
        Product result = productService.createProduct(requestCreate);

        // Then
        Assertions.assertThat(result.getProductId()).isEqualTo(1L);
        Assertions.assertThat(result.getProductName()).isEqualTo("TestProduct");
    }

    @Test
    void updateProduct() {
        // Mock findById and save methods
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(product);

        // When
        ProductResponse result = productService.updateProduct(1L, requestUpdate);

        // Then
        Assertions.assertThat(result.getProductId()).isEqualTo(1L);
        Assertions.assertThat(result.getDescription()).isEqualTo("Updated Product Description");
        Assertions.assertThat(result.getUnitSellPrice()).isEqualTo(120.0);
    }

    @Test
    void deleteProductById() {
        // Mock findById and delete methods
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        Mockito.doNothing().when(productRepository).delete(ArgumentMatchers.any(Product.class));

        // When
        String result = productService.deleteProductById(product);

        // Then
        Assertions.assertThat(result).isEqualTo("Product deleted");
    }
}
