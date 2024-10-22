package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.ProductCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ProductUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ProductResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.repository.CategoryRepository;
import com.shoppingapp.shoppingapp.repository.ProductRepository;
import com.shoppingapp.shoppingapp.repository.ShopRepository;
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

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ShopRepository shopRepository;

    private ProductCreationRequest requestCreate;
    private ProductUpdateRequest requestUpdate;
    private Product product;
    private ProductResponse response;
    private Category category;
    private Shop shop;

    @BeforeEach
    void initData() {
        // Dummy Category
        category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Electronics");

        // Dummy Shop
        shop = Shop.builder()
                .shopId(1L)
                .shopName("Shop1")
                .build();

        // Initialize ProductCreationRequest for test cases
        requestCreate = ProductCreationRequest.builder()
                .productName("TestProduct")
                .category(1L)  // Using category ID
                .shop(1L)      // Using shop ID
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

        // Mock repository to return dummy data
        Mockito.when(shopRepository.findById(anyLong())).thenReturn(Optional.of(shop));
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        Mockito.when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(product);
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
        Mockito.when(shopRepository.findById(anyLong())).thenReturn(Optional.of(shop));
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        Mockito.when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(product);

        // When

        var result = productService.updateProduct(1L, requestUpdate);

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
        String result = productService.deleteProductById(1L);

        // Then
        Assertions.assertThat(result).isEqualTo("");
    }

    @Test
    void getProductById_ProductNotFound() {
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> productService.getProductById(1L))
                .isInstanceOf(AppException.class)
                .hasMessage(ErrorCode.PRODUCT_NOT_EXISTED.getMessage())
                .extracting("errorCode")
                .isEqualTo(ErrorCode.PRODUCT_NOT_EXISTED);
    }

    @Test
    void createProduct_CategoryNotFound() {
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> productService.createProduct(requestCreate))
                .isInstanceOf(AppException.class)
                .hasMessage(ErrorCode.CATEGORY_NOT_EXISTED.getMessage())
                .extracting("errorCode")
                .isEqualTo(ErrorCode.CATEGORY_NOT_EXISTED);
    }

    @Test
    void createProduct_ShopNotFound() {
        Mockito.when(shopRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> productService.createProduct(requestCreate))
                .isInstanceOf(AppException.class)
                .hasMessage(ErrorCode.SHOP_NOT_EXISTED.getMessage())
                .extracting("errorCode")
                .isEqualTo(ErrorCode.SHOP_NOT_EXISTED);
    }

    @Test
    void updateProduct_ProductNotFound() {
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> productService.updateProduct(1L, requestUpdate))
                .isInstanceOf(AppException.class)
                .hasMessage(ErrorCode.PRODUCT_NOT_EXISTED.getMessage())
                .extracting("errorCode")
                .isEqualTo(ErrorCode.PRODUCT_NOT_EXISTED);
    }

    @Test
    void deleteProductById_ProductNotFound() {
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> productService.deleteProductById(1L))
                .isInstanceOf(AppException.class)
                .hasMessage(ErrorCode.PRODUCT_NOT_EXISTED.getMessage())
                ;
    }



}
