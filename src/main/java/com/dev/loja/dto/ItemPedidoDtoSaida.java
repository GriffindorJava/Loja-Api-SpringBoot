package com.dev.loja.dto;

import com.dev.loja.model.ItemPedido;

public class ItemPedidoDtoSaida {
    public String produtoId;
    public String produto;
    public String precoUnitario;
    public String quantidade;
    public String subtotal;

    public ItemPedidoDtoSaida(ItemPedido itemPedido){
        this.produtoId = itemPedido.getProduto().getId().toString();
        this.produto = itemPedido.getProduto().getNome();
        this.precoUnitario = itemPedido.getProduto().getPrecoVenda().toString();
        this.quantidade = String.valueOf(itemPedido.getQuantidade());
        this.subtotal = itemPedido.getSubtotal().toString();
    }
}
