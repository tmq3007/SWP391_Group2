package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.models.Transaction;
import com.shoppingapp.shoppingapp.repository.ShopRepository;
import com.shoppingapp.shoppingapp.repository.TransactionRepository;
import com.shoppingapp.shoppingapp.service.TransactionService;
import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ShopRepository shopRepository;


    @Override
    public Transaction createTransaction(Orders order) {
        Shop shop = shopRepository.findById(order.getShop().getShopId()).get();

        Transaction transaction = new Transaction();
        transaction.setShop(shop);
        transaction.setUser(order.getShop().getUser());
        transaction.setOrder(order);

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByShopId(Shop shop) {
        return transactionRepository.findByShop_ShopId(shop.getShopId());
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
