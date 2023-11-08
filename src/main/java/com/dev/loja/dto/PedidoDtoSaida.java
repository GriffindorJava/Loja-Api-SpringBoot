package com.dev.loja.dto;

import com.dev.loja.model.Pedido;

import java.util.List;

public class PedidoDtoSaida {
    public String numero;
    public String data;
    public String formaPagamento;
    public String frete;
    public String descontos;
    public String total;
    public String status;
    public UserDtoSaida cliente;
    public String cep;
    public List<ItemPedidoDtoSaida> itens;

    public PedidoDtoSaida(Pedido pedido){
        this.numero = (pedido.getNumero() != null)? pedido.getNumero().toString(): null;
        this.data = pedido.getData().toString();
        this.formaPagamento = pedido.getFormaPagamento().toString();
        this.frete = pedido.getFrete().toString();
        this.descontos = pedido.getDescontos().toString();
        this.total = pedido.getTotal().toString();
        this.status = pedido.getPedidoStatus().toString();
        this.cliente = new UserDtoSaida(pedido.getUser());
        this.cep = pedido.getCep();
        this.itens = pedido.getItens().stream().map(ItemPedidoDtoSaida::new).toList();
    }

}
