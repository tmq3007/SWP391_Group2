package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.ProductCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ProductUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ProductResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.mapper.ProductMapper;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.repository.CategoryRepository;
import com.shoppingapp.shoppingapp.repository.ProductRepository;
import com.shoppingapp.shoppingapp.repository.ShopRepository;
import com.shoppingapp.shoppingapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service


public class ProductServiceImpl implements ProductService {
    

    @Autowired
    private ProductMapper productMapper;


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ShopRepository shopRepository;

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product getProductById(Long ProductId) {
        return productRepository.findById(ProductId).get();
    }

    @Override
    public Product createProduct(ProductCreationRequest request) {

        Product product = productMapper.toProduct(request);

        var shopOptional = shopRepository.findById(Long.valueOf(request.getShop()));
        if (!shopOptional.isPresent()) {
            throw new AppException(ErrorCode.SHOP_NOT_EXISTED);
        }

        product.setShop(shopOptional.get());

        var categoryOptional = categoryRepository.findById(Long.valueOf(request.getCategory()));
        if (!categoryOptional.isPresent()) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }

        product.setCategory(categoryOptional.get());

        if(productRepository.existsByProductName(request.getProductName())) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        return productRepository.save(product);
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.updateProduct(product, request);

        var shopOptional = shopRepository.findById(Long.valueOf(request.getShop()));
        if (!shopOptional.isPresent()) {
            throw new AppException(ErrorCode.SHOP_NOT_EXISTED);
        }

        product.setShop(shopOptional.get());
        var categoryOptional = categoryRepository.findById(Long.valueOf(request.getCategory()));
        if (!categoryOptional.isPresent()) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }

        product.setCategory(categoryOptional.get());


        return productMapper.toProductResponse(productRepository.save(product));
    }


    @Override
    public String deleteProductById(Product product) {
        productRepository.delete(product);
        return "Product deleted";
    }
}
