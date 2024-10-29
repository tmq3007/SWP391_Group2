package com.shoppingapp.shoppingapp.service;
import java.util.List;
import java.util.Optional;

import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.models.Shop;;

public interface ShopService {
    List<ShopResponse> getAllShops();
    ShopResponse getShopById(long ShopId);
    ShopResponse createShop(ShopCreationRequest request);
    ShopResponse updateShop(ShopUpdateRequest request, long ShopId);
    String deleteShop(Long shopId);
    int getTotalShops();
    Long getShopIdByUserId(Long userId);
    Shop getShopProfile(String jwt);
    Shop getShopByUserId(Long userId);
}
