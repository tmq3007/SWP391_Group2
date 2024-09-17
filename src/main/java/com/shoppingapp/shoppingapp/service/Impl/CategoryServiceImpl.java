package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.models.Category;
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
        return categoryRepository.findById(id).get();
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Category category) {
        categoryRepository.delete(category);
        return "Category deleted successfully with ID:" + category.getCategoryId();
    }
}
