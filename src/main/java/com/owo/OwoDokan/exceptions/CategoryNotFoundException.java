package com.owo.OwoDokan.exceptions;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(Long id)
    {
        super("Category with id: "+id+" Not found");
    }

    public CategoryNotFoundException(String message)
    {
        super(message);
    }
}