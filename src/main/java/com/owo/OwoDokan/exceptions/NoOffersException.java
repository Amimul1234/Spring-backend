package com.owo.OwoDokan.exceptions;

public class NoOffersException extends RuntimeException{
    public NoOffersException() {
        super("No offers available");
    }
}
