package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(("/api/v1/categories"))
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



}
