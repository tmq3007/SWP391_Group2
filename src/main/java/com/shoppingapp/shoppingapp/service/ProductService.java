package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.ProductCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ProductUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ProductResponse;
import com.shoppingapp.shoppingapp.models.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long ProductId);

    Product createProduct(ProductCreationRequest request);

    ProductResponse updateProduct(Long productId, ProductUpdateRequest request);

    String deleteProductById(Long productId);

    List<Product> getAllProductsByShopId(Long shopId);
    String deleteAmountAfterMadeOrder(Long productId, int amount);
}
