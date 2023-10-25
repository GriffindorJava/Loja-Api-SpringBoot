package com.dev.loja.dto;

import jakarta.validation.constraints.Min;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "produtoId")
public class CarrinhoItem {

    public Long produtoId;
    @Min(value = 1L, message = "A quantidade precisa ser positiva")
    public int quantidade;

}
