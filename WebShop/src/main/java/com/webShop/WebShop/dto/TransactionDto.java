package com.webShop.WebShop.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionDto {
    public long id;
    public List<ServicesDto> services;
    public double amount;
    public LocalDateTime merchantOrderTimestamp;
    public int merchantOrderId;
}
