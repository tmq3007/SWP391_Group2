package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.CategoryCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.CategoryUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.CategoryResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.mapper.CategoryMapper;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.repository.CategoryRepository;
import com.shoppingapp.shoppingapp.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public CategoryResponse getCategory(Long id) {
        return categoryMapper.toCategoryResponse(categoryRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED)));
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
    }

    @Override
    public Category addCategory(CategoryCreationRequest request) {
        log.info("Service: Category added");
        if(categoryRepository.existsByCategoryName(request.getCategoryName())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        Category category = categoryMapper.toCategory(request);
        return categoryRepository.save(category);
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId,CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
       categoryMapper.updateCategory(category,request);

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public String deleteCategory(Category category) {
        categoryRepository.delete(category);
        return "Category deleted successfully with ID:" + category.getCategoryId();
    }
}
