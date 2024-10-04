package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.dto.response.UserResponse;
import com.shoppingapp.shoppingapp.mapper.ShopMapper;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.service.Impl.UserServiceImpl;
import com.shoppingapp.shoppingapp.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/shops")
public class ShopController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopMapper shopMapper;


    //get all shop
    @GetMapping()
    public ResponseEntity<List<Shop>>  getAllCategories() {
        return ResponseEntity.ok(shopService.getAllShops()) ;
    }
    // get shop by id
    @GetMapping("{shopId}")
    ApiResponse<ShopResponse> getShop(@PathVariable("shopId") Long shopId) {
        return ApiResponse.<ShopResponse>builder().result(shopService.getShopById(shopId)).build();
    }

    //add new shop
    @PostMapping("")
    ApiResponse<ShopResponse> createShop(@RequestBody ShopCreationRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(shopService.createShop(request));
        return apiResponse;
    }
    //upadate a shop by id
    @PatchMapping("/{shopId}")
    ResponseEntity<ShopResponse> updateShop(@RequestBody ShopUpdateRequest request, @PathVariable("shopId") Long shopId) {
    return ResponseEntity.ok(shopService.updateShop(request,shopId));
    }
    @DeleteMapping("/{shopId}")
    ApiResponse<String> deleteShop(@PathVariable("shopId") Long shopId) {
        shopService.deleteShop(shopId);
        return ApiResponse.<String>builder().result("Shop is deleted").build();
    }



}
