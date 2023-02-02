package com.webShop.WebShop.controller;

import com.webShop.WebShop.WebShopApplication;
import com.webShop.WebShop.dto.*;
import com.webShop.WebShop.enums.TransactionStatus;
import com.webShop.WebShop.mapper.TransactionDtoMapper;
import com.webShop.WebShop.model.Services;
import com.webShop.WebShop.model.Transaction;
import com.webShop.WebShop.model.User;
import com.webShop.WebShop.service.TransactionService;
import com.webShop.WebShop.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    @Autowired
    RestTemplate restTemplate;

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllForUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        List<TransactionDto> transactions = new ArrayList<>();
        List<Transaction> userTran = userService.userWithTransactions(user.getId()).getTransactions();
       if(userTran.size() != 0){
           for (Transaction t : userTran){
               transactions.add(transactionDtoMapper.fromTransactionToDto(t));
//               TransactionDto dto = new TransactionDto();
//               dto.id = t.getId();
//               dto.amount = t.getAmount();
//               dto.merchantOrderId = t.getMerchantOrderId();
//               dto.merchantOrderTimestamp = t.getMerchantOrderTimestamp();
//               for(Services s : t.getTransactionServices()){
//                   dto.services.add(new ServicesDto(0, s.getName(), s.getDescription(),s.getPrice()));
//               }
//               transactions.add(dto);
               log.info("Accessing from user to all transactions.");
           }
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

    @PostMapping("/status")
    public ResponseEntity<HttpStatus> changeStatus(@RequestBody BankStatusTransactionDto bankStatusTransactionDto){
       if(transactionService.changeBankStatus(bankStatusTransactionDto)!=null)
        return new ResponseEntity<>(HttpStatus.OK);
       else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/paypal")
    public ResponseEntity<String> paypalPayment(@RequestBody PayPalWebshopDto ppDto){
        System.out.println(ppDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PayPalOrderDto order = new PayPalOrderDto(ppDto.getPrice(), ppDto.getDescription());
        PayPalPaymentDTO response = restTemplate.postForObject("http://localhost:8081/paypal/send", order, PayPalPaymentDTO.class);
        System.out.println(response);
        Transaction t = transactionService.findByMerchantOrderId((int)ppDto.getMerchantOrderId());
        t.setPaymentId(response.getPaymentId());
        t.setStatus(response.getStatus());
        t.setPaymentMethod("paypal");
        transactionService.save(t);
        System.out.println(response.getLink());
        return new ResponseEntity<>(response.getLink(), HttpStatus.OK);
    }

    @PostMapping("/paypalUpdate")
    public ResponseEntity<String> paypalUpdate(@RequestBody PayPalPaymentDTO dto){
        Transaction t = transactionService.findByPaymentId(dto.getPaymentId());
        t.setStatus(TransactionStatus.SUCCESS);
        transactionService.save(t);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/bitcoin")
    public ResponseEntity<String> bitcoinPayment(@RequestBody BitcoinWebshopDto bitcoinDto){
        System.out.println(bitcoinDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String redirectionLink = restTemplate.postForObject("http://localhost:8081/bitcoin/savetransaction", bitcoinDto, String.class);
        Transaction t = transactionService.findByMerchantOrderId((int)bitcoinDto.getMerchantOrderId());
        t.setPaymentId(bitcoinDto.getOrderId());
        t.setStatus(TransactionStatus.PAYMENT_REQUESTED);
        t.setPaymentMethod("crypto");
        t.setCurrency("EUR");
        transactionService.save(t);
        return new ResponseEntity<>(redirectionLink, HttpStatus.OK);
    }

    @PostMapping("/bitcoinUpdate")
    public ResponseEntity<String> bitcoinUpdate(@RequestBody BitcoinPaymentDTO dto){
        Transaction t = transactionService.findByPaymentId(dto.getOrderId());
        t.setStatus(TransactionStatus.SUCCESS);
        transactionService.save(t);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
