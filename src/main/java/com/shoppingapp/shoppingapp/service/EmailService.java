package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.RejectShopRequest;
import com.shoppingapp.shoppingapp.dto.request.ResetPasswordRequest;
import com.shoppingapp.shoppingapp.dto.request.VerifyShopRequest;

public interface EmailService {

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

    void verifyShop(VerifyShopRequest verifyShopRequest);

    void rejectShop(RejectShopRequest rejectShopRequest);
}