package com.webShop.WebShop.repository;

import com.webShop.WebShop.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value= "select t from Transaction t join fetch User u where u.id = ?1", nativeQuery = true)
    List<Transaction> findTransactionsForUser(long id);
    Transaction findByPaymentId(String paymentId);
    Transaction findByMerchantOrderId(long merchantOrderId);
}
