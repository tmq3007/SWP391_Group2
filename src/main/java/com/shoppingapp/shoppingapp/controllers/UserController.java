package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.*;
import com.shoppingapp.shoppingapp.dto.response.CustomerResponse;
import com.shoppingapp.shoppingapp.dto.response.UserResponse;
import com.shoppingapp.shoppingapp.dto.response.VendorResponse;
import com.shoppingapp.shoppingapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    UserService userService;

    @GetMapping("/getUsername/{id}")
    ApiResponse<?> getUserName(@PathVariable("id") Long userId) {
        System.out.println("Id"+userId);
        return ApiResponse.builder().
                result(userService.userName(userId)).
                build();
    }

    @PostMapping("/sign-up")
    ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAll())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") Long userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userId")  Long userId, @RequestBody UserUpdateRequest request) {
        System.out.println("Update");
        System.out.println("Object"+request.toString());
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @GetMapping("/total-vendors")
    ApiResponse<Integer> getTotalVendors() {
        return ApiResponse.<Integer>builder()
                .result(userService.getTotalVendors())
                .build();
    }

    @PutMapping("/ban/{userId}")
    ApiResponse<String> banUser(@PathVariable("userId") Long userId) {
        userService.banUser(userId);
        return ApiResponse.<String>builder().result("User has been banned").build();
    }

    @PutMapping("/updatePhone/{userId}/{userPhone}")
    ApiResponse<?> updateUserPhone(@PathVariable("userId") String userId, @PathVariable("userPhone") String userPhone) {
        System.out.println(userId +" "+userPhone);
       return ApiResponse.builder().result(userService.updateUserPhone(Long.parseLong(userId), userPhone)).build();
    }

    @PutMapping("/unban/{userId}")
    ApiResponse<String> unbanUser(@PathVariable("userId") Long userId) {
        userService.unbanUser(userId);
        return ApiResponse.<String>builder().result("User has been unbanned").build();
    }

    @GetMapping("/all-vendors")
    ApiResponse<List<VendorResponse>> getVendors() {
        return ApiResponse.<List<VendorResponse>>builder()
                .result(userService.getVendors())
                .build();
    }

    @GetMapping("/all-customers")
    ApiResponse<List<CustomerResponse>> getCustomers() {
        return ApiResponse.<List<CustomerResponse>>builder()
                .result(userService.getCustomers())
                .build();
    }

    @PutMapping("/profile/{userId}")
    ApiResponse<String> updateProfile(@PathVariable("userId") Long userId,@RequestBody ProfileUpdateRequest request) {
        userService.updateProfile(userId,request);
        return ApiResponse.<String>builder().result("User updated").build();
    }

    @PutMapping("/change-password/{userId}")
    ApiResponse<String> changePassword(@PathVariable("userId") Long userId,@RequestBody ChangePasswordRequest request) {
        userService.changePassword(userId,request);
        return ApiResponse.<String>builder().result("Password Changed").build();
    }


}
