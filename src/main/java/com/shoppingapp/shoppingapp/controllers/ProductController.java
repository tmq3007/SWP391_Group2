package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.ProductCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ProductUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ProductResponse;
import com.shoppingapp.shoppingapp.mapper.ProductMapper;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor

public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    //Build Get All Product REST API
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    //Build Get All Product REST API
    @GetMapping("/shop/{shopId}")
    public ApiResponse<List<Product>> getAllProductsByShopId(@PathVariable Long shopId) {
        return ApiResponse.<List<Product>>builder()
                .result(productService.getAllProductsByShopId(shopId))
                .build();
    }

    //Build Get Product REST API

    @GetMapping("{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") Long ProductId) {
        return ResponseEntity.ok(productService.getProductById(ProductId));
    }

    //Build Add Product REST API
    @PostMapping()
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductCreationRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productService.createProduct(request));

        return apiResponse;
    }


    //Build Update Product REST API
    @PatchMapping("{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductUpdateRequest product,
                                                         @PathVariable("productId") Long ProductId) {
        return ResponseEntity.ok(productService.updateProduct(ProductId, product));
    }

    //Build Delete Product REST API
    @DeleteMapping("{productId}")
    ApiResponse<String> deleteShop(@PathVariable("productId") Long productId) {
        productService.deleteProductById(productId);
        return ApiResponse.<String>builder().result("Product is deleted").build();
    }
}
