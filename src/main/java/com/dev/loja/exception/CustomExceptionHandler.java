package com.dev.loja.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        DefaultError error = this.exceptionBodyBuider(e, request);
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicatedEntityException.class)
    public ResponseEntity<?> duplicatedEntity(DuplicatedEntityException e, HttpServletRequest request){
        DefaultError error = this.exceptionBodyBuider(e, request);
        error.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(FileOperationException.class)
    public ResponseEntity<?> fileError(FileOperationException e, HttpServletRequest request){
        DefaultError error = this.exceptionBodyBuider(e, request);
        error.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(BadRequestException e, HttpServletRequest request){
        DefaultError error = this.exceptionBodyBuider(e, request);
        error.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler(CustomJwtVerificationException.class)
    public ResponseEntity<?> jwtFail(CustomJwtVerificationException e, HttpServletRequest request){
        DefaultError error = this.exceptionBodyBuider(e, request);
        error.setStatus(HttpStatus.FORBIDDEN.value());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationFields(MethodArgumentNotValidException e, HttpServletRequest request){
        ValidationError error = new ValidationError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Erro(s) de validação");
        error.setPath(request.getRequestURI());

        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            error.addErrors(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private DefaultError exceptionBodyBuider(RuntimeException e, HttpServletRequest request){
        DefaultError error = new DefaultError();
        error.setTimestamp(Instant.now());
        error.setPath(request.getRequestURI());
        error.setMessage(e.getMessage());
        return error;
    }
}
