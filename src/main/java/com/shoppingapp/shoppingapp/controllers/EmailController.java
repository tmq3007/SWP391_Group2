package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.RejectShopRequest;
import com.shoppingapp.shoppingapp.dto.request.ResetPasswordRequest;
import com.shoppingapp.shoppingapp.dto.request.VerifyShopRequest;
import com.shoppingapp.shoppingapp.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        emailService.resetPassword(request);
        return ApiResponse.<String>builder().result("Reset password successfully").build();
    }

    @PostMapping("/announce-verify-shop")
    public ApiResponse<String> verifyShop(@RequestBody VerifyShopRequest verifyShopRequest) {
        emailService.verifyShop(verifyShopRequest);
        return ApiResponse.<String>builder().result("Verify shop successfully").build();
    }

    @PostMapping("/announce-reject-shop")
    public ApiResponse<String> rejectShop(@RequestBody RejectShopRequest rejectShopRequest) {
        emailService.rejectShop(rejectShopRequest);
        return ApiResponse.<String>builder().result("Reject shop successfully").build();
    }
}