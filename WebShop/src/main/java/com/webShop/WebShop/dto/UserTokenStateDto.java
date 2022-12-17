package com.webShop.WebShop.dto;

import com.webShop.WebShop.enums.UserType;

public class UserTokenStateDto {
    public String accessToken;
    public UserType role;

    public UserTokenStateDto(String accessToken, UserType role) {
        this.accessToken = accessToken;
        this.role = role;
    }
}