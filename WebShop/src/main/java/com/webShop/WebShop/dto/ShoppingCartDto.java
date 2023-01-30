package com.webShop.WebShop.dto;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDto {
    public long id;
    public List<ServicesDto> services = new ArrayList<>();
    public double amount;

}
