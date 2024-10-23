package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.UnverifiedShopCreationRequest;
import com.shoppingapp.shoppingapp.models.UnverifiedShop;

import java.util.List;
import java.util.Optional;

public interface UnverifiedShopService {
    UnverifiedShop getUnverifiedShop();
    UnverifiedShop addUnverifiedShop(UnverifiedShopCreationRequest unverifiedShop);
    void verifyShop(Long unverifiedShopId);
    Long getUnverifiedShopIdByUserId(Long userId);

    List<UnverifiedShop> getAllUnverifiedShops();
}
