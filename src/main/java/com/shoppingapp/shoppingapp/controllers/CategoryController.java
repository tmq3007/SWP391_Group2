package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.CategoryCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.CategoryUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.CategoryResponse;
import com.shoppingapp.shoppingapp.mapper.CategoryMapper;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Category>>  getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories()) ;
    }

    @GetMapping("/{categoryId}")
    public CategoryResponse getCategory (@PathVariable("categoryId") Long categoryId) {
        return  (categoryService.getCategory(categoryId));
    }

    @PostMapping("")
    public ApiResponse<Category> addCategory (@RequestBody CategoryCreationRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(categoryService.addCategory(request));

        return apiResponse ;
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryUpdateRequest category,
                                                   @PathVariable("categoryId") Long categoryId){

        return ResponseEntity.ok(categoryService.updateCategory(categoryId, category)) ;
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        Category categoryObj = categoryService.getCategoryById(categoryId);
        String deleteMsg = "";
        if(categoryObj != null){
            deleteMsg = categoryService.deleteCategory(categoryObj);
        }
        return ResponseEntity.ok(deleteMsg);
    }

}
