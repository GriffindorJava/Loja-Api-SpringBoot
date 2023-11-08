package com.dev.loja.beans;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Util {

    /**
     * Acessa api externa para retornar o valor do frete
     * @param cepOrigem Cep de origem
     * @param cepDestino Cep de destino
     * @return BigDecimal
     */
    public static BigDecimal calculaFrete(String cepOrigem, String cepDestino){
        if(cepDestino.isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal("16.00"); //Valor fictício
    }
}
