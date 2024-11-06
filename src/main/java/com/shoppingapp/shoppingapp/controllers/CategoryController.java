package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.CategoryCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.CategoryUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.CategoryResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.mapper.CategoryMapper;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping(("/api/v1/categories"))
@AllArgsConstructor
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping()
    public ApiResponse<List<Category>>  getAllCategories() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(categoryService.getAllCategories());
        return apiResponse;
    }

    @GetMapping("/{categoryId}")
    public ApiResponse<CategoryResponse> getCategory (@PathVariable("categoryId") Long categoryId) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(categoryService.getCategory(categoryId));
        return apiResponse;
    }

    @PostMapping("")
    public ApiResponse<Category> addCategory (@RequestBody CategoryCreationRequest request) {
        log.info("Controller: addCategory");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(categoryService.addCategory(request));

        return apiResponse ;
    }

    @PatchMapping("/{categoryId}")
    public ApiResponse<CategoryResponse> updateCategory(@RequestBody CategoryUpdateRequest category,
                                                   @PathVariable("categoryId") Long categoryId){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(categoryService.updateCategory(categoryId, category));
        return apiResponse ;
    }

    @DeleteMapping("/{categoryId}")
    public ApiResponse<String> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        Category categoryObj = categoryService.getCategoryById(categoryId);
        ApiResponse<String> apiResponse = new ApiResponse<>();

        if (categoryObj != null) {
            categoryService.deleteCategory(categoryObj); // Service deletes the category
            apiResponse.setCode(0);
            apiResponse.setResult("Category deleted successfully");
        } else {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }

        return apiResponse;
    }


    @GetMapping("/get-top-10-by-most-products")
    public ApiResponse<List<Category>> getTop10ByMostProducts() {
        return ApiResponse.<List<Category>>builder()
                .result(categoryService.getTop10ByMostProducts())
                .build();
    }
}
