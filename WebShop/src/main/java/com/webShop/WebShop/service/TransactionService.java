package com.webShop.WebShop.service;

import com.webShop.WebShop.model.Transaction;
import com.webShop.WebShop.model.User;
import com.webShop.WebShop.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        transaction.setMerchantOrderId((long)(new Random().nextDouble()*1234567L));
        transaction.setMerchantOrderTimestamp(LocalDateTime.now());
        transaction.setUser(user);
        transaction.setAmount(user.getShoppingCart().getAmount());
        transaction.setCurrency("EUR");
        transaction.setStatus("new");
        transaction.setPaymentMethod("");
        transaction.setPaymentId("");
        return transactionRepository.save(transaction);
    }

    public Transaction findByPaymentId(String paymentId) { return transactionRepository.findByPaymentId(paymentId);}
    public Transaction findByMerchantOrderId(long merchantOrderId) { return transactionRepository.findByMerchantOrderId(merchantOrderId);}
    public void save(Transaction t) {transactionRepository.save(t);}
}
