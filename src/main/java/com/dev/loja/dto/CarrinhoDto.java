package com.dev.loja.dto;

import com.dev.loja.enums.FormaPagamento;
import com.dev.loja.model.User;

import java.util.List;

public record CarrinhoDto(FormaPagamento formaPagamento, List<CarrinhoItem> itens) {
}
