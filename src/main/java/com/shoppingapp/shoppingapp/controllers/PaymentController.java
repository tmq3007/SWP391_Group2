package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.request.PaymentCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.PaymentUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.PaymentResponse;
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
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{paymentId}")
    public PaymentResponse getPayment(@PathVariable("paymentId") Long paymentId) {
        return  paymentService.getPayment(paymentId);
    }

    @PostMapping("")
    public ApiResponse<Payment> createPayment(@RequestBody PaymentCreationRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(paymentService.addPayment(request));
        return apiResponse;
    }

    @PatchMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> updatePayment(@RequestBody PaymentUpdateRequest request,
                                                 @PathVariable("paymentId") Long paymentId ) {


        return ResponseEntity.ok(paymentService.updatePayment(request,paymentId));
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<String> deleteUser(@PathVariable("paymentId") Long paymentId) {
        Payment paymentObj = paymentService.getPaymentById(paymentId);
        String deleteMsg = "";
        if(paymentObj != null){
           deleteMsg =  paymentService.deletePayment(paymentObj);
        }
        return ResponseEntity.ok(deleteMsg);
    }

}
