package com.dev.loja.exception;

public class FileOperationException extends RuntimeException{
    public FileOperationException(String message) {
        super(message);
    }
}
