package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.repository.ShopRepository;
import com.shoppingapp.shoppingapp.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(maxAge = 3600)
@RestController
public class ShopController {
    @Autowired
    private ShopService shopService;

    @PostMapping("/shops")
    public ResponseEntity<List<Shop>> getAllShops() {
        return ResponseEntity.ok(shopService.getAllShops());
    }
    @GetMapping("/shops/{ShopId}")
    public ResponseEntity<Shop> getShop(@PathVariable("shopId") Long ShopId) {
        return ResponseEntity.ok(shopService.getShopById(ShopId));
    }
    @PatchMapping("/shops/{ShopId}")
    public ResponseEntity<Shop> updateShop(@RequestBody Shop shop,@PathVariable("shopId") Long ShopId) {
        Shop shopObj = shopService.getShopById(ShopId);
        if(shopObj != null) {
            shopObj.setShopName(shop.getShopName());
            shopObj.setAddress(shop.getAddress());
            shopObj.setCity(shop.getCity());
            shopObj.setCountry(shop.getCountry());
            shopObj.setPhone(shop.getPhone());
            shopObj.setState(shop.getState());

        }
        return ResponseEntity.ok(shopService.updateShop(shop));
    }
    @DeleteMapping("/shops/{ShopId}")
    public ResponseEntity<String> deleteShop(@RequestBody Shop shop,@PathVariable("shopId") Long ShopId) {
        Shop shopObj = shopService.getShopById(ShopId);
        String deleteMsg = null;
        if(shopObj != null) {
            deleteMsg = shopService.deleteShop(shopObj);
        }
        return ResponseEntity.ok(deleteMsg);
    }



}
