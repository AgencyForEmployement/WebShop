package com.webShop.WebShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PayPalWebshopDto {

    private double price;
    private String description;
    private long merchantOrderId;
}