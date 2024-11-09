package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.CategoryCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.CategoryUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.CategoryResponse;
import com.shoppingapp.shoppingapp.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    CategoryResponse getCategory(Long id);
    Category getCategoryById(Long id);
    Category addCategory(CategoryCreationRequest request);
    CategoryResponse updateCategory(Long categoryId,CategoryUpdateRequest request);
    String deleteCategory(Category category);

    List<Category> getTop10ByMostProducts();
    CategoryResponse updateCategory2(Long categoryId );
}
