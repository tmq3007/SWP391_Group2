package com.shoppingapp.shoppingapp.service;
import java.util.List;
import com.shoppingapp.shoppingapp.models.Shop;

public interface ShopService {
    List<Shop> getAllShops();
    Shop getShopById(long ShopId);
    Shop addShop(Shop shop);
    Shop updateShop(Shop shop);
    String deleteShop(Shop shop);
}
