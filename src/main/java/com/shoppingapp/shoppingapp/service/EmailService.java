package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.ResetPasswordRequest;

public interface EmailService {

    void resetPassword(ResetPasswordRequest resetPasswordRequest);
}
