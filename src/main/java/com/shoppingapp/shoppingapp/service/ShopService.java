package com.shoppingapp.shoppingapp.service;
import java.util.List;
import java.util.Optional;

import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.dto.response.StatisticShopResponse;
import com.shoppingapp.shoppingapp.models.Shop;;

public interface ShopService {
    List<ShopResponse> getAllShops();
    ShopResponse getShopById(Long ShopId);
    ShopResponse createShop(ShopCreationRequest request);
    ShopResponse updateShop(ShopUpdateRequest request, Long ShopId);
    String deleteShop(Long shopId);
    int getTotalShops();
    Long getShopIdByUserId(Long userId);
    Shop getShopProfile(String jwt);
    Shop getShopByUserId(Long userId);
    List<StatisticShopResponse> getAllStatisticShops();
    StatisticShopResponse getStatisticShop(Long shopId);
}
