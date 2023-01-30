package com.webShop.WebShop.service;

import com.webShop.WebShop.model.Transaction;
import com.webShop.WebShop.model.User;
import com.webShop.WebShop.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> allUserTransactions(long id) { return transactionRepository.findTransactionsForUser(id);}

    public Transaction saveTransaction(User user) {
        Transaction transaction = new Transaction();
        transaction.setTransactionServices(user.getShoppingCart().getServices());
        transaction.setMerchantOrderId(new Random().nextLong());
        transaction.setMerchantOrderTimestamp(new Date());
        transaction.setUser(user);
        transaction.setAmount(user.getShoppingCart().getAmount());
        return transactionRepository.save(transaction);
    }
}
