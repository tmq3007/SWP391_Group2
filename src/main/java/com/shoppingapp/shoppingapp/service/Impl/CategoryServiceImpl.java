package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.CategoryCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.CategoryUpdateRequest;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.exceptions.GlobalExceptionHandler;
import com.shoppingapp.shoppingapp.exceptions.ResourceNotFoundException;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.repository.CategoryRepository;
import com.shoppingapp.shoppingapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Category not found"));
    }

    @Override
    public Category addCategory(CategoryCreationRequest request) {
        Category category = new Category();

        if(categoryRepository.existsByCategoryName(request.getCategoryName())) {
            throw new ResourceNotFoundException(ErrorCode.CATEGORY_EXISTED);
        }

        category.setCategoryName(request.getCategoryName());
       category.setDescription(request.getDescription());
       category.setPicture(request.getPicture());
       category.setIsActive(request.getIsActive());
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long categoryId,CategoryUpdateRequest request) {
        Category category = getCategory(categoryId);
        category.setDescription(request.getDescription());
        category.setPicture(request.getPicture());
        category.setIsActive(request.getIsActive());

        return categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Category category) {
        categoryRepository.delete(category);
        return "Category deleted successfully with ID:" + category.getCategoryId();
    }
}
