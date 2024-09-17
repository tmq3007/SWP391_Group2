package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.repository.ProductRepository;
import com.shoppingapp.shoppingapp.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service


public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product getProductById(Long ProductId) {
        return productRepository.findById(ProductId).get();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public String deleteProductById(Product product) {
        productRepository.delete(product);
        return "Product deleted";
    }
}
