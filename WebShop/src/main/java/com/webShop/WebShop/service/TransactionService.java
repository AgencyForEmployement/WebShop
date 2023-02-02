package com.webShop.WebShop.service;

import com.webShop.WebShop.dto.BankStatusTransactionDto;
import com.webShop.WebShop.dto.TransactionDto;
import com.webShop.WebShop.enums.TransactionStatus;
import com.webShop.WebShop.model.Transaction;
import com.webShop.WebShop.model.User;
import com.webShop.WebShop.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
        transaction.setMerchantOrderId(generateRandomNumber());
        transaction.setMerchantOrderTimestamp(LocalDateTime.now());
        transaction.setUser(user);
        transaction.setAmount(user.getShoppingCart().getAmount());
        transaction.setStatus(TransactionStatus.IN_PROGRESS);
        //transaction.setMerchantOrderId((long)(new Random().nextDouble()*1234567L));
        //transaction.setCurrency("EUR");
        //transaction.setStatus("new");
        //transaction.setPaymentMethod("");
        //transaction.setPaymentId("");
        return transactionRepository.save(transaction);
    }

    private int generateRandomNumber() {
        int m = (int) Math.pow(10, 10 - 1);
        return m + new Random().nextInt(9 * m);
    }

    public Transaction findTransactionByMerchantId(int merchantId){
        return transactionRepository.findByMerchantOrderId(merchantId);
    }

    public Transaction changeBankStatus(BankStatusTransactionDto bankStatusTransactionDto){
        Transaction transaction = findTransactionByMerchantId(bankStatusTransactionDto.merchantId);
        if(transaction != null){
            transaction.setStatus(bankStatusTransactionDto.status);
            transactionRepository.save(transaction);
            return transaction;
        } else return null;
    }

    public Transaction findByPaymentId(String paymentId) { return transactionRepository.findByPaymentId(paymentId);}
    public Transaction findByMerchantOrderId(int merchantOrderId) { return transactionRepository.findByMerchantOrderId(merchantOrderId);}
    public void save(Transaction t) {transactionRepository.save(t);}
}
