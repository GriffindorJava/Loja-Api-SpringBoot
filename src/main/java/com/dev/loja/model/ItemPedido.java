package com.dev.loja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
    private Pedido pedido;
    @ManyToOne
    private Produto produto;
    private int quantidade;
    private BigDecimal subtotal;

    public BigDecimal getSubtotal(){
        return produto.getPrecoVenda().multiply(new BigDecimal(quantidade));
    }
}
