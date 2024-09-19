package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategory(Long id);
    Category addCategory(Category category);
    Category updateCategory(Category category);
    String deleteCategory(Category category);
}
