package com.dev.loja.dto;

import com.dev.loja.enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CarrinhoDto(@NotNull FormaPagamento formaPagamento, List<CarrinhoItem> itens) {
}
