package com.shoppingapp.shoppingapp.service;
import java.util.List;

import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;;

public interface ShopService {
    List<ShopResponse> getAllShops();
    ShopResponse getShopById(long ShopId);
    ShopResponse createShop(ShopCreationRequest request);
    ShopResponse updateShop(ShopUpdateRequest request, long ShopId);
    String deleteShop(Long shopId);

    int getTotalShops();
}
