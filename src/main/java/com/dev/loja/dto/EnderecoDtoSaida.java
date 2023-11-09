package com.dev.loja.dto;

import com.dev.loja.model.Endereco;

public class EnderecoDtoSaida {

    public Long id;
    public String cep;
    public String rua;
    public String bairro;
    public String cidade;
    public String estado;
    public String numero;
    public Boolean principal;

    public EnderecoDtoSaida(Endereco endereco){
        this.id = endereco.getId();
        this.cep = endereco.getCep();
        this.rua = endereco.getRua();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
        this.numero = endereco.getNumero();
        this.principal = endereco.getPrincipal();
    }
}
