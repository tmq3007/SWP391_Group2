package com.shoppingapp.shoppingapp.service;
import java.util.List;
import java.util.Optional;

import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.models.Shop;

public interface ShopService {
    List<Shop> getAllShops();
    ShopResponse getShopById(long ShopId);
    Shop createShop(ShopCreationRequest request);
    ShopResponse updateShop(ShopUpdateRequest request, long ShopId);
    String deleteShop(Long shopId);

    int getTotalShops();
    Optional<Long> getShopIdByUserId(Long userId);
    Shop getShopProfile(String jwt);
}
