package com.dev.loja.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "produtoId")
public class CarrinhoItem {
    public Long produtoId;
    public Integer quantidade;

}
