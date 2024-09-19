package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long ProductId);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    String deleteProductById(Product product);
}
