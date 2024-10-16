package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.models.Transaction;
import jakarta.persistence.criteria.Order;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Orders order);

    List<Transaction> getTransactionsByShopId(Shop shop);

    List<Transaction> getAllTransactions();
}
