package com.dev.loja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private User user;

    private String cep;

    private String rua;

    private String bairro;

    private String cidade;

    private String estado;

    private String numero;

    private Boolean principal;
}
