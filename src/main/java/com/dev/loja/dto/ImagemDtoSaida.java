package com.dev.loja.dto;

import com.dev.loja.model.Imagem;

public class ImagemDtoSaida {
    public String caminho;
    public ImagemDtoSaida(Imagem imagem){
        this.caminho = imagem.getCaminho();
    }
    public ImagemDtoSaida(){}
}
