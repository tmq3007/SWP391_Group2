package com.shoppingapp.shoppingapp.mapper;

import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.UnverifiedShopCreationRequest;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.models.UnverifiedShop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface UnverifiedShopMapper {
    @Mapping(target = "user", ignore = true)
    UnverifiedShop toUnverifiedShop(UnverifiedShopCreationRequest unverifiedShopCreationRequest);
}
