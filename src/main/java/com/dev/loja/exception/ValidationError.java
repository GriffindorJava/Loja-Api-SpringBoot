package com.dev.loja.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class ValidationError extends DefaultError{
    private List<FieldMessage> errors = new ArrayList<>();

    public void addErrors(String fieldName, String message){
        this.errors.add(new FieldMessage(fieldName, message));
    }
}
