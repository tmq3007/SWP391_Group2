package com.shoppingapp.shoppingapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.CategoryCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.CategoryUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.CategoryResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.service.CategoryService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService  categoryService;

    private CategoryCreationRequest requestCreate;
    private CategoryUpdateRequest requestUpdate;
    private CategoryResponse response;
    private CategoryResponse responseUpdate;
    private Category category;

    @BeforeEach
    void initData(){
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
        responseUpdate = CategoryResponse.builder()
                .categoryId(1L)
                .categoryName("CateTest")
                .description("Test Description Update")
                .picture("Test Picture Update")
                .isActive(true)
                .build();

        response = CategoryResponse.builder()
                .categoryId(1L)
                .categoryName("CateTest")
                .description("Test Description")
                .picture("Test Picture")
                .isActive(true)
                .build();
        category = Category.builder()
                .categoryId(1L)
                .categoryName("CateTest")
                .description("Test Description")
                .picture("Test Picture")
                .isActive(true)
                .build();
    }

    //test valid add category
    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN"  })
    void addCategory_validRequest_success() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(requestCreate);

        Mockito.when(categoryService.addCategory(ArgumentMatchers.any()))
                .thenReturn(category);

        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.categoryId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.categoryName").value("CateTest"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.description").value("Test Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.picture").value("Test Picture"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.isActive").value(true)

                );
    }

    //test add exist category
    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    void addCategory_categoryExist_fail() throws Exception {
         ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(requestCreate);

         Mockito.when(categoryService.addCategory(ArgumentMatchers.any()))
                .thenThrow(new AppException(ErrorCode.CATEGORY_EXISTED));

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1009))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Category existed"));
    }

    //Test update success
    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    void updateCategory_validRequest_success() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(requestUpdate);

        Mockito.when(categoryService.updateCategory(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
                .thenReturn(responseUpdate);

        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/categories/{categoryId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.categoryId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.categoryName").value("CateTest"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.description").value("Test Description Update"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.picture").value("Test Picture Update"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.isActive").value(true));
    }

    //Test update category not exist
    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN"  })
    void updateCategory_categoryNotExist_fail() throws Exception {

        Mockito.when(categoryService.updateCategory(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
                .thenThrow(new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        // Prepare request body
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(requestUpdate);

        // Perform the request and expect a failure due to non-existent category
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/categories/{categoryId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // Expect 400 BAD_REQUEST
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1010))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Category not existed"));
    }


    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN"  })
    void deleteCategory_success() throws Exception {
        // Given
        // Mock the category retrieval to return a valid category
        Mockito.when(categoryService.getCategoryById(1L))
                .thenReturn(category);


        // Mock successful deletion behavior
        Mockito.when(categoryService.deleteCategory(ArgumentMatchers.any()))
                .thenReturn("Category deleted successfully");

        // When & Then for successful case
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/categories/{categoryId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("Category deleted successfully"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN"  })
    void deleteCategory_categoryNotExist_fail() throws Exception {
        // Given
        // Mock the category retrieval to return null (category does not exist)
        Mockito.when(categoryService.getCategoryById(1L))
                .thenReturn(null);

        // When & Then for failure case
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/categories/{categoryId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1010))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Category not existed"));
    }


    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "CUSTOMER","VENDOR" })
    void getAllCategories_success() throws Exception {
        List<Category> categories = Arrays.asList(category);
        ApiResponse<List<Category>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(categories);

        Mockito.when(categoryService.getAllCategories())
                .thenReturn(categories);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].categoryId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].categoryName").value("CateTest"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "CUSTOMER","VENDOR" })
    void getCategory_Success() throws Exception {
        // Given
        Long categoryId = 1L;

        Mockito.when(categoryService.getCategory(categoryId))
                .thenReturn(response);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/categories/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.categoryId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.categoryName").value("CateTest"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.description").value("Test Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.picture").value("Test Picture"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.isActive").value(true));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "CUSTOMER","VENDOR" })
    void getCategory_categoryNotExist_fail() throws Exception {
        // Given
        Long categoryId = 1L;

         Mockito.when(categoryService.getCategory(categoryId))
                .thenThrow(new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/categories/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1010)) // The predefined error code for "Category not existed"
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Category not existed")); // The error message
    }


}