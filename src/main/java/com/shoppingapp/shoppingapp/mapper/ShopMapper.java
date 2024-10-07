package com.shoppingapp.shoppingapp.mapper;

import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.models.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "products", ignore = true)
//    @Mapping(target = "order", ignore = true)
    Shop toShop(ShopCreationRequest shopCreationRequest);

    void updateShop(@MappingTarget Shop shop, ShopUpdateRequest shopUpdateRequest);
    ShopResponse toShopResponse(Shop shop);
}
