package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.PaymentCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.PaymentUpdateRequest;

import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.models.Category;
import com.shoppingapp.shoppingapp.models.Payment;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/payments")
@AllArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ApiResponse<List<Payment>> getAllPayments() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(paymentService.getAllPayments());
        return apiResponse;
    }

    @GetMapping("/{paymentId}")
    public ApiResponse<PaymentResponse> getPayment(@PathVariable("paymentId") Long paymentId) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(paymentService.getPayment(paymentId));
        return  apiResponse;
    }

    @PostMapping("")
    public ApiResponse<Payment> createPayment(@RequestBody PaymentCreationRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(paymentService.addPayment(request));
        return apiResponse;
    }

    @PatchMapping("/{paymentId}")
    public ApiResponse<PaymentResponse> updatePayment(@RequestBody PaymentUpdateRequest request,
                                                 @PathVariable("paymentId") Long paymentId ) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(paymentService.updatePayment(request,paymentId));
        return apiResponse;
    }

    @DeleteMapping("/{paymentId}")
    public ApiResponse<String> deletePayment(@PathVariable("paymentId") Long paymentId) {
        Payment paymentObj = paymentService.getPaymentById(paymentId);

        ApiResponse apiResponse = new ApiResponse();
        if(paymentObj != null){
             paymentService.deletePayment(paymentObj);
            apiResponse.setCode(0);
           apiResponse.setResult("Deleted Payment Successfully");
        }
        else {
            throw new AppException(ErrorCode.PAYMENT_NOT_FOUND);
        }
        return apiResponse;
    }

}
