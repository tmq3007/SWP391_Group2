package com.shoppingapp.shoppingapp.mapper;

import com.shoppingapp.shoppingapp.dto.request.ProductCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ProductUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ProductResponse;
import com.shoppingapp.shoppingapp.models.Product;
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
    void updateProduct(@MappingTarget Product product, ProductUpdateRequest productCreationRequest);

    ProductResponse toProductResponse(Product product);
}
