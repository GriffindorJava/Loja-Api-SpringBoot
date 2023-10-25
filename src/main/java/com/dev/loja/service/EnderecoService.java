package com.dev.loja.service;

import com.dev.loja.dto.ProdutoDtoEntrada;
import com.dev.loja.dto.ProdutoDtoSaida;
import com.dev.loja.model.Produto;
import com.dev.loja.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
@AllArgsConstructor
public class EnderecoService {
    private EnderecoRepository enderecoRepository;

}
