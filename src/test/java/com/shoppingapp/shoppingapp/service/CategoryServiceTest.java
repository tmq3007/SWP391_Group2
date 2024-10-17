package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.CategoryCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.CategoryUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.CategoryResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.repository.CategoryRepository;
import com.shoppingapp.shoppingapp.service.Impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
 class CategoryServiceTest {

    @Autowired
    CategoryService  categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    private CategoryCreationRequest requestCreate;
    private CategoryUpdateRequest requestUpdate;
    private CategoryResponse response;
    private Category category;
    private CategoryResponse responseUpdate;
    @BeforeEach
    void initData() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        requestCreate = CategoryCreationRequest.builder()
                .categoryName("CateTest")
                .description("Test Description")
                .picture("Test Picture")
                .isActive(true)
                .build();

        requestUpdate = CategoryUpdateRequest.builder()
                .description("Test Description Update")
                .picture("Test Picture Update")
                .isActive(true)
                .build();

        category = Category.builder()
                .categoryId(1L)
                .categoryName("CateTest")
                .description("Test Description")
                .picture("Test Picture")
                .isActive(true)
                .build();

        response = CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .picture(category.getPicture())
                .isActive(category.getIsActive())
                .build();
        responseUpdate = CategoryResponse.builder()
                .categoryId(1L)
                .categoryName("CateTest")
                .description("Test Description Update")
                .picture("Test Picture Update")
                .isActive(true)
                .build();
    }

    @Test
    void getAllCategories() {
        // Mocking findAll method to return a list of categories
        List<Category> categories = Arrays.asList(category);
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

        // When
        List<Category> result = categoryService.getAllCategories();

        // Then
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0).getCategoryName()).isEqualTo("CateTest");
    }

    @Test
    void getCategoryById() {
        // Mocking findById method
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        // When
        Category result = categoryService.getCategoryById(1L);

        // Then
        Assertions.assertThat(result.getCategoryId()).isEqualTo(1L);
        Assertions.assertThat(result.getCategoryName()).isEqualTo("CateTest");
    }
    @Test
    void getCategoryById_categoryNotFound_fail() throws Exception{
        // Mocking findById method
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> categoryService.getCategoryById(1L))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(ErrorCode.CATEGORY_NOT_EXISTED.getMessage());
    }

    @Test
    void addCategory_success() {
        // Given
        Mockito.when(categoryRepository.existsByCategoryName("CateTest")).thenReturn(false);
        Mockito.when(categoryRepository.save(ArgumentMatchers.any(Category.class))).thenReturn(category);

        // When
        var result = categoryService.addCategory(requestCreate);

        // Then
        Assertions.assertThat(result.getCategoryId()).isEqualTo(1L);
        Assertions.assertThat(result.getCategoryName()).isEqualTo("CateTest");
    }

    @Test
    void addCategory_categoryExist_fail() throws Exception {
        // Given
        Mockito.when(categoryRepository.existsByCategoryName(anyString())).thenReturn(true);
        Assertions.assertThatThrownBy(() -> categoryService.addCategory(requestCreate))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(ErrorCode.CATEGORY_EXISTED.getMessage());
    }

    @Test
    void updateCategory_success() {
        // Mocking findById method
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(ArgumentMatchers.any(Category.class))).thenReturn(category);

        // When
        var result = categoryService.updateCategory(1L, requestUpdate);

        // Then
        Assertions.assertThat(result.getCategoryId()).isEqualTo(1L);
        Assertions.assertThat(result.getDescription()).isEqualTo("Test Description Update");
    }

    @Test
    void updateCategory_categoryNotFound_fail() throws Exception {
        // Mocking findById method
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> categoryService.updateCategory(1L, requestUpdate))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(ErrorCode.CATEGORY_NOT_EXISTED.getMessage());
    }

    @Test
    void deleteCategory_success() {
        // Given
        Mockito.when(categoryRepository.existsById(anyLong())).thenReturn(true); // Ensure category exists
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        Mockito.doNothing().when(categoryRepository).delete(ArgumentMatchers.any(Category.class));

        // When
        String result = categoryService.deleteCategory(category); // Assuming deleteCategory accepts a Category object

        // Then
        Assertions.assertThat(result).isEqualTo("Category deleted successfully with ID:" + category.getCategoryId());
    }

    @Test
    void deleteCategory_categoryNotFound_fail() throws Exception {
        // Mocking findById and delete methods
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> categoryService.deleteCategory(category))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(ErrorCode.CATEGORY_NOT_EXISTED.getMessage());
    }
}
