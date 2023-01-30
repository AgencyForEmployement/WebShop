package com.webShop.WebShop.controller;

import com.webShop.WebShop.WebShopApplication;
import com.webShop.WebShop.dto.TransactionDto;
import com.webShop.WebShop.mapper.TransactionDtoMapper;
import com.webShop.WebShop.model.Transaction;
import com.webShop.WebShop.model.User;
import com.webShop.WebShop.service.TransactionService;
import com.webShop.WebShop.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionDtoMapper transactionDtoMapper;
    private final UserService userService;
    final static Logger log = Logger.getLogger(WebShopApplication.class.getName());

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllForUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        List<TransactionDto> transactions = new ArrayList<>();
        List<Transaction> userTran = userService.userWithTransactions(user.getId()).getTransactions();
        for (Transaction t : userTran){
           transactions.add(transactionDtoMapper.fromTransactionToDto(t));
            log.info("Accessing from user to all transactions.");
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/transaction")
    public ResponseEntity<TransactionDto> saveNewTransaction(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        TransactionDto transaction = transactionDtoMapper.fromTransactionToDto(transactionService.saveTransaction(user));
        log.info("New transaction created with transaction id " + transaction.id);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
