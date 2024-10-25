package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.RejectShopRequest;
import com.shoppingapp.shoppingapp.dto.request.UnverifiedShopCreationRequest;
import com.shoppingapp.shoppingapp.models.UnverifiedShop;

import java.util.List;

public interface UnverifiedShopService {
    UnverifiedShop getUnverifiedShop();
    UnverifiedShop addUnverifiedShop(UnverifiedShopCreationRequest unverifiedShop);
    void verifyShop(Long unverifiedShopId);
    void rejectShop(Long unverifiedShopId);
    Long getUnverifiedShopIdByUserId(Long userId);

    List<UnverifiedShop> getAllUnverifiedShops();
}
