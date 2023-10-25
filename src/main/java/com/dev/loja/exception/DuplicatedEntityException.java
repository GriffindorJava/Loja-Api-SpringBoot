package com.dev.loja.exception;

public class DuplicatedEntityException extends RuntimeException{
    public DuplicatedEntityException(String message) {
        super(message);
    }
}
