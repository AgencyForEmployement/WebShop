package com.webShop.WebShop.dto;

import com.webShop.WebShop.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    public long id;
    public List<ServicesDto> services = new ArrayList<>();
    public double amount;
    public LocalDateTime merchantOrderTimestamp;
    public int merchantOrderId;
    public TransactionStatus status;
    public String paymentMethod;
}
