package com.shoppingapp.shoppingapp.controllers;

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
    public ResponseEntity<Payment> getPayment(@PathVariable("paymentId") Long paymentId) {
        return  ResponseEntity.ok(paymentService.getPayment(paymentId));
    }

    @PostMapping("")
    public ResponseEntity<Payment> getPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.addPayment(payment));
    }

    @PatchMapping("/{paymentId}")
    public ResponseEntity<Payment> updatePayment(@RequestBody Payment payment,
                                                 @PathVariable("paymentId") Long paymentId ) {
        Payment paymentObj = paymentService.getPayment(paymentId);
        if(paymentObj != null){
            paymentObj.setPaymentType(payment.getPaymentType());
            paymentObj.setIsActive(payment.getIsActive());

            paymentService.updatePayment(paymentObj);
        }

        return ResponseEntity.ok(paymentService.updatePayment(paymentObj));
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<String> deleteUser(@PathVariable("paymentId") Long paymentId) {
        Payment paymentObj = paymentService.getPayment(paymentId);
        String deleteMsg = "";
        if(paymentObj != null){
           deleteMsg =  paymentService.deletePayment(paymentObj);
        }
        return ResponseEntity.ok(deleteMsg);
    }

}
