package com.shoppingapp.shoppingapp.controllers;

import com.shoppingapp.shoppingapp.dto.request.ApiResponse;
import com.shoppingapp.shoppingapp.dto.response.ShopResponse;
import com.shoppingapp.shoppingapp.dto.response.TransactionResponse;
import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.models.Transaction;
import com.shoppingapp.shoppingapp.service.ShopService;
import com.shoppingapp.shoppingapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")

public class TransactionController {
    private final TransactionService transactionService;
    private final ShopService shopService;

    @PostMapping
     ApiResponse<TransactionResponse> createTransaction(@RequestBody Orders order) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(transactionService.createTransaction(order));
        return apiResponse;
    }


    @GetMapping()
    public ResponseEntity<List<Transaction>>  getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions()) ;
    }


}
