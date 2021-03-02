package com.owo.OwoDokan.exceptions;

public class ShopNotFoundException extends RuntimeException{
    public ShopNotFoundException(String mobileNumber)
    {
        super("Shop with mobile number: "+mobileNumber+" Not Found");
    }
}
