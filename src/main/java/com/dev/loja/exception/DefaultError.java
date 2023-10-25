package com.dev.loja.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
public class DefaultError implements Serializable {
    private Instant timestamp;
    private Integer status;
    private String message;
    private String path;
}
