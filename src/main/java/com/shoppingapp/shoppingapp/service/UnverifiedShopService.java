package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.UnverifiedShopCreationRequest;
import com.shoppingapp.shoppingapp.models.UnverifiedShop;

public interface UnverifiedShopService {
    UnverifiedShop getUnverifiedShop();
    UnverifiedShop addUnverifiedShop(UnverifiedShopCreationRequest unverifiedShop);
    void verifyShop(Long unverifiedShopId);
}
