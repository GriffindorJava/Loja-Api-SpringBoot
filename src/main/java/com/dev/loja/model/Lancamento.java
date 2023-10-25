package com.dev.loja.model;

import com.dev.loja.enums.ProdutoStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Produto produto;
    private Long codigoBarras;
    @Transient
    private Integer quantidade;
    private LocalDateTime dataLancamento;
    private LocalDateTime dataSaida;
    private ProdutoStatus produtoStatus = ProdutoStatus.DISPONIVEL;

}
