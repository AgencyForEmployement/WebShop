package com.webShop.WebShop.dto;

import java.util.Date;
import java.util.List;

public class TransactionDto {
    public long id;
    public List<ServicesDto> services;
    public double amount;
    public Date merchantOrderTimestamp;
    public long merchantOrderId;
}
