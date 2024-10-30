package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.ShopCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ShopUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.dto.response.StatisticShopResponse;
import com.shoppingapp.shoppingapp.mapper.ShopMapper;
import com.shoppingapp.shoppingapp.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ApiResponse<List<ShopResponse>> getAllShop() {
        return ApiResponse.<List<ShopResponse>>builder()
                .result(shopService.getAllShops())
                .build();
    }
    // get shop by id
    @GetMapping("{shopId}")
    ApiResponse<ShopResponse> getShop(@PathVariable("shopId") Long shopId) {
        return ApiResponse.<ShopResponse>builder().result(shopService.getShopById(shopId)).build();
    }

    // get shop by user id
    @GetMapping("/getShopByUserId/{userId}")
    ApiResponse<?> getShopByUserId(@PathVariable("userId") Long userId) {
        return ApiResponse.builder().result(shopService.getShopIdByUserId(userId)).build();
    }

    @GetMapping("/getShopInformationByUserId/{id}")
    ApiResponse<?> getShopInformationByUserId(@PathVariable("id") Long id) {
        System.out.println("Get shop by id!");
        return ApiResponse.builder()
                .result(shopService.getShopByUserId(id))
                .build();
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

    @GetMapping("/total-shops")
    ApiResponse<Integer> getTotalShops() {
        return ApiResponse.<Integer>builder().result(shopService.getTotalShops()).build();
    }

    @GetMapping("/get-shopId/{userId}")
    ApiResponse<Long> getShopIdByUserId(@PathVariable("userId") Long userId) {
        return ApiResponse.<Long>builder().result(shopService.getShopIdByUserId(userId)).build();
    }

    @GetMapping("/get-all-statistics-shops")
    ApiResponse<List<StatisticShopResponse>> getAllStatisticsShops() {
        return ApiResponse.<List<StatisticShopResponse>>builder().result(shopService.getAllStatisticShops()).build();
    }

    @GetMapping("/get-statistic-shop/{shopId}")
    public ApiResponse<StatisticShopResponse> getStatisticShop(@PathVariable Long shopId) {
        StatisticShopResponse statisticShopResponse = shopService.getStatisticShop(shopId);
        return ApiResponse.<StatisticShopResponse>builder().result(statisticShopResponse).build();
    }

}
