package com.shoppingapp.shoppingapp.mapper;

import com.shoppingapp.shoppingapp.dto.request.ProductCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.ProductUpdateRequest;
import com.shoppingapp.shoppingapp.dto.request.TransactionCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.TransactionUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.ProductResponse;
import com.shoppingapp.shoppingapp.dto.response.TransactionResponse;
import com.shoppingapp.shoppingapp.models.Product;
import com.shoppingapp.shoppingapp.models.Transaction;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface TransactionMapper {
    @Mapping(target = "user",ignore = true)
    @Mapping(target = "shop", ignore = true)
    Transaction toTransaction(TransactionCreationRequest transactionCreationRequest);

    @Mapping(target = "user",ignore = true)
    @Mapping(target = "shop", ignore = true)
    void updateTransaction(@MappingTarget Transaction transaction, TransactionUpdateRequest transactionUpdateRequest);

    TransactionResponse toTransactionResponse(Transaction transaction);
}
