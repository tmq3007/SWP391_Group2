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
@RequestMapping("/api/v1")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @GetMapping("/shops")
    public ResponseEntity<List<Shop>> getAllShops() {
        return ResponseEntity.ok(shopService.getAllShops());
    }
    @GetMapping("/shops/{shopId}")
    public ResponseEntity<Shop> getShop(@PathVariable("shopId") Long shopId) {
        return ResponseEntity.ok(shopService.getShopById(shopId));
    }
    @PostMapping("/shops")
    public ResponseEntity<Shop> getShop(@RequestBody Shop shop) {
        return ResponseEntity.ok(shopService.addShop(shop));
    }
    @PatchMapping("/shops/{shopId}")
    public ResponseEntity<Shop> updateShop(@RequestBody Shop shop,@PathVariable("shopId") Long shopId) {
        Shop shopObj = shopService.getShopById(shopId);
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
    @DeleteMapping("/shops/{shopId}")
    public ResponseEntity<String> deleteShop(@PathVariable("shopId") Long shopId) {
        Shop shopObj = shopService.getShopById(shopId);
        String deleteMsg = null;
        if(shopObj != null) {
            deleteMsg = shopService.deleteShop(shopObj);
        }
        return ResponseEntity.ok(deleteMsg);
    }



}
