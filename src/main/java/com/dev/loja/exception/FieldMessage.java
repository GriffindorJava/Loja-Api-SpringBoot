package com.dev.loja.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class FieldMessage implements Serializable{
    private String fieldName;
    private String message;
}
