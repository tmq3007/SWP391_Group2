

package com.shoppingapp.shoppingapp.mapper;

import com.shoppingapp.shoppingapp.dto.request.ProductCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ProductUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ProductResponse;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category",ignore = true)
    @Mapping(target = "shop", ignore = true)
    Product toProduct(ProductCreationRequest productCreationRequest);

    @Mapping(target = "category",ignore = true)
    @Mapping(target = "shop", ignore = true)
    void updateProduct(@MappingTarget Product product, ProductUpdateRequest productUpdateRequest);

    ProductResponse toProductResponse(Product product);

    // Mapping methods to convert between Category <-> Long and Shop <-> Long
    default Long map(Category category) {
        return category != null ? category.getCategoryId() : null;
    }

    default Long map(Shop shop) {
        return shop != null ? shop.getShopId() : null;
    }

    default Category mapCategory(Long categoryId) {
        return categoryId != null ? new Category(categoryId) : null; // Assuming Category has a constructor with ID
    }

    default Shop mapShop(Long shopId) {
        return shopId != null ? new Shop(shopId) : null; // Assuming Shop has a constructor with ID
    }
}
