package com.webShop.WebShop.dto;

import com.webShop.WebShop.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PayPalPaymentDTO {

    private String paymentId;
    private String link;
    private String status;
}