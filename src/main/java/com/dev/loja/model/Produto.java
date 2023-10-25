package com.dev.loja.model;

import com.dev.loja.dto.ProdutoDtoEntrada;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Categoria categoria;
    @OneToMany(mappedBy = "produto")
    private List<Imagem> imagens = new ArrayList<>();
    @NotBlank
    @Column(unique = true)
    private String nome;
    private BigDecimal precoCompra;
    private BigDecimal precoVenda;
    private Integer estoqueAtual = 0;
    private Integer estoqueMinimo = 0;
    @Lob
    private String descricao;
    private Boolean ativo = true;

    public Produto(ProdutoDtoEntrada produtoDtoEntrada){
        this.categoria = produtoDtoEntrada.categoria;
        this.nome = produtoDtoEntrada.nome.trim();
        this.descricao = produtoDtoEntrada.descricao;
        this.estoqueMinimo = produtoDtoEntrada.estoqueMinimo;
        this.precoCompra = produtoDtoEntrada.precoCompra;
        this.precoVenda = produtoDtoEntrada.precoVenda;
        this.ativo = produtoDtoEntrada.ativo;
    }

}
