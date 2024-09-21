package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.CategoryCreationRequest;
import com.shoppingapp.shoppingapp.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategory(Long id);
    Category addCategory(CategoryCreationRequest request);
    Category updateCategory(Category category);
    String deleteCategory(Category category);
}
