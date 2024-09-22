package com.shoppingapp.shoppingapp.mapper;

import com.shoppingapp.shoppingapp.dto.request.CategoryCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.CategoryUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.CategoryResponse;
import com.shoppingapp.shoppingapp.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryCreationRequest categoryCreationRequest);
    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest categoryCreationRequest);
    CategoryResponse toCategoryResponse(Category category);
}
