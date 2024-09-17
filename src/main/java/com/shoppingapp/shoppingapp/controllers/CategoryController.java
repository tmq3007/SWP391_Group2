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


}
