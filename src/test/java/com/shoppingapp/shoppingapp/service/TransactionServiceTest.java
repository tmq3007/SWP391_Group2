package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.models.Shop;
import com.shoppingapp.shoppingapp.models.Transaction;
import com.shoppingapp.shoppingapp.repository.ShopRepository;
import com.shoppingapp.shoppingapp.repository.TransactionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private ShopRepository shopRepository;

    private Orders order;
    private Shop shop;
    private Transaction transaction;

    @BeforeEach
    void initData() {
        // Dummy Shop
        shop = new Shop();
        shop = Shop.builder()
                .shopId(1L)
                .shopName("Shop1")
                .build();

        // Dummy Order
        order = new Orders();
        order.setShop(shop);

        // Dummy Transaction
        transaction = new Transaction();
        transaction = Transaction.builder()
                .transId(1L)
                .shop(shop)
                .order(order)
                .build();

        // Mock repository to return dummy data
        Mockito.when(shopRepository.findById(anyLong())).thenReturn(Optional.of(shop));
        Mockito.when(transactionRepository.save(ArgumentMatchers.any(Transaction.class))).thenReturn(transaction);
    }

    @Test
    void createTransaction() {
        // When
        Transaction result = transactionService.createTransaction(order);

        // Then
        Assertions.assertThat(result.getTransId()).isEqualTo(1L);
        Assertions.assertThat(result.getShop().getShopName()).isEqualTo("Shop1");
    }

    @Test
    void getTransactionsByShopId() {
        // Mock the findByShop_ShopId method to return a list of transactions
        List<Transaction> transactions = Arrays.asList(transaction);
        Mockito.when(transactionRepository.findByShop_ShopId(anyLong())).thenReturn(transactions);

        // When
        List<Transaction> result = transactionService.getTransactionsByShopId(shop);

        // Then
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0).getTransId()).isEqualTo(1L);
    }

    @Test
    void getAllTransactions() {
        // Mock the findAll method to return a list of transactions
        List<Transaction> transactions = Arrays.asList(transaction);
        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);

        // When
        List<Transaction> result = transactionService.getAllTransactions();

        // Then
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0).getTransId()).isEqualTo(1L);
    }

    @Test
    void createTransaction_ShopNotFound() {
        // Mock the findById method to return empty
        Mockito.when(shopRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        Assertions.assertThatThrownBy(() -> transactionService.createTransaction(order))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Shop not existed");
    }

    @Test
    void createTransaction_SaveFailed() {
        // Mock the save method to throw an exception
        Mockito.when(transactionRepository.save(ArgumentMatchers.any(Transaction.class)))
                .thenThrow(new RuntimeException("Failed to save transaction"));

        // When & Then
        Assertions.assertThatThrownBy(() -> transactionService.createTransaction(order))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Failed to save transaction");
    }

    @Test
    void getTransactionsByShopId_ShopNotFound() {
        Mockito.when(transactionRepository.findByShop_ShopId(anyLong())).thenReturn(Arrays.asList());

        // When
        List<Transaction> result = transactionService.getTransactionsByShopId(shop);

        // Then
        Assertions.assertThat(result).isEmpty();
    }
}
