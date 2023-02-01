package com.webShop.WebShop.dto;

import com.webShop.WebShop.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BankStatusTransactionDto {
    public int merchantId;
    public TransactionStatus status;
}
