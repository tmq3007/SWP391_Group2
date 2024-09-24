package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor

public class ProductController {

    @Autowired
    private ProductService productService;

    //Build Get All Product REST API
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    //Build Get Product REST API
    @GetMapping("{ProductId}")
    public ResponseEntity<Product> getProductById(@PathVariable("ProductId") Long ProductId) {
        return ResponseEntity.ok(productService.getProductById(ProductId));
    }

    //Build Add Product REST API
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    //Build Update Product REST API
    @PatchMapping("{ProductId}")
    public ResponseEntity<Product> updateProduct(@PathVariable("ProductId") Long ProductId, @RequestBody Product product) {
        Product productObj = productService.getProductById(ProductId);
        if(productObj != null) {
            productObj.setProductName(product.getProductName());
            productObj.setCategory(product.getCategory());
            productObj.setShop(product.getShop());
            productObj.setDescription(product.getDescription());
            productObj.setMeasurementUnit(product.getMeasurementUnit());
            productObj.setUnitBuyPrice(product.getUnitBuyPrice());
            productObj.setUnitSellPrice(product.getUnitSellPrice());
            productObj.setDiscount(product.getDiscount());
            productObj.setStock(product.getStock());
            productObj.setPictureUrl(product.getPictureUrl());
            productObj.setIsActive(product.getIsActive());

            productService.updateProduct(productObj);
        }
        return ResponseEntity.ok(productService.updateProduct(productObj));
    }

    //Build Delete Product REST API
    @DeleteMapping("{ProductId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("ProductId") Long ProductId) {
        Product productObj = productService.getProductById(ProductId);
        String deleteMsg = null;
        if(productObj != null) {
            deleteMsg=productService.deleteProductById(productObj);
        }
        return ResponseEntity.ok(deleteMsg);
    }
}
