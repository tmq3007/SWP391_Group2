package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.UnverifiedShopCreationRequest;
import com.shoppingapp.shoppingapp.mapper.UnverifiedShopMapper;
import com.shoppingapp.shoppingapp.models.UnverifiedShop;
import com.shoppingapp.shoppingapp.service.UnverifiedShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")

public class UnverifiedShopController {
    @Autowired
    private UnverifiedShopService unverifiedShopService;
    @Autowired
    private UnverifiedShopMapper unverifiedShopMapper;

    @PostMapping("/request-shop-creation")
    ApiResponse<String> addUnverifiedShop(@RequestBody UnverifiedShopCreationRequest request) {
        unverifiedShopService.addUnverifiedShop(request);

        return ApiResponse.<String>builder().result("Request has been sent").build();
    }

    @PutMapping("/verify-shop/{unverified-shop-id}")
    ApiResponse<String> verifyShop(@PathVariable("unverified-shop-id")  Long unverifiedShopId) {
        unverifiedShopService.verifyShop(unverifiedShopId);
        return ApiResponse.<String>builder().result("Shop has been verified").build();
    }

    @GetMapping("/get-unverifed-shopid/{userId}")
    ApiResponse<Long> getUnverifiedShop(@PathVariable("userId") Long userId) {
        return ApiResponse.<Long>builder().result(unverifiedShopService.getUnverifiedShopIdByUserId(userId)).build();
    }

    @GetMapping("/get-all-unverified-shops")
    ApiResponse<Iterable<UnverifiedShop>> getAllUnverifiedShops() {
        return ApiResponse.<Iterable<UnverifiedShop>>builder().result(unverifiedShopService.getAllUnverifiedShops()).build();
    }

    @PutMapping("/reject-shop/{unverified-shop-id}")
    ApiResponse<String> rejectShop(@PathVariable("unverified-shop-id") Long unverifiedShopId) {
        unverifiedShopService.rejectShop(unverifiedShopId);
        return ApiResponse.<String>builder().result("Shop request has been rejected").build();
    }

    @DeleteMapping("/delete-rejected-request/{unverifiedShopId}")
    ApiResponse<String> deleteRejectedRequest(@PathVariable("unverifiedShopId") Long unverifiedShopId) {
        unverifiedShopService.deleteRejectedRequest(unverifiedShopId);
        return ApiResponse.<String>builder().result("Request is deleted").build();
    }

}
