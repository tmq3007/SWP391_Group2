package com.shoppingapp.shoppingapp.controllers;

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

    @GetMapping()
    public ResponseEntity<List<Category>>  getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories()) ;
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory (@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    @PostMapping("")
    public ResponseEntity<Category> getCategory (@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.addCategory(category)) ;
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category,
                                                   @PathVariable("categoryId") Long categoryId){
        Category categoryObj = categoryService.getCategory(categoryId);
        if(categoryObj != null){
            categoryObj.setCategoryName(category.getCategoryName());
            categoryObj.setDescription(category.getDescription());
            categoryObj.setPicture(category.getPicture());
            categoryObj.setIsActive(category.getIsActive());
            categoryService.updateCategory(categoryObj);
        }
        return ResponseEntity.ok(categoryService.updateCategory(categoryObj));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        Category categoryObj = categoryService.getCategory(categoryId);
        String deleteMsg = "";
        if(categoryObj != null){
            deleteMsg = categoryService.deleteCategory(categoryObj);
        }
        return ResponseEntity.ok(deleteMsg);
    }

}
