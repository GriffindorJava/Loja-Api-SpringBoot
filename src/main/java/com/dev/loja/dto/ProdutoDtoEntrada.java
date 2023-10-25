package com.dev.loja.dto;

import com.dev.loja.model.Categoria;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProdutoDtoEntrada {
    @NotNull
    public Categoria categoria;
    @NotBlank
    public String nome;
    @DecimalMin(value = "0.00")
    public BigDecimal precoCompra;
    @DecimalMin(value = "0.00")
    public BigDecimal precoVenda;
    @Min(0)
    public int estoqueMinimo;
    @Lob
    public String descricao;
    public boolean ativo = true;
}
