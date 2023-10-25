package com.dev.loja.exception;

public class CustomJwtVerificationException extends RuntimeException {
    public CustomJwtVerificationException(String message) {
        super(message);
    }
}
