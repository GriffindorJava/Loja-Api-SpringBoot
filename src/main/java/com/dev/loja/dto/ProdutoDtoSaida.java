package com.dev.loja.dto;

import com.dev.loja.controller.ProdutoController;
import com.dev.loja.model.Produto;
import jakarta.persistence.Lob;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

public class ProdutoDtoSaida extends RepresentationModel<ProdutoDtoSaida> {
    public String id;
    public CategoriaDtoSaida categoria;
    public String nome;
    public String precoCompra;
    public String precoVenda;
    public String estoqueAtual;
    public String estoqueMinimo;
    @Lob
    public String descricao;
    public Boolean ativo;
    public List<ImagemDtoSaida> imagens;
    public ProdutoDtoSaida(Produto produto){
        this.id = String.valueOf(produto.getId());
        this.categoria = new CategoriaDtoSaida(produto.getCategoria());
        this.nome = produto.getNome();
        this.precoCompra = produto.getPrecoCompra().toString();
        this.precoVenda = produto.getPrecoVenda().toString();
        this.estoqueAtual = String.valueOf(produto.getEstoqueAtual());
        this.estoqueMinimo = String.valueOf(produto.getEstoqueMinimo());
        this.descricao = produto.getDescricao();
        this.ativo = produto.getAtivo();
        this.imagens = produto.getImagens().stream().map(ImagemDtoSaida::new).toList();
        this.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProdutoController.class)
                        .buscarPorId(produto.getId())).withSelfRel());
        this.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProdutoController.class)
                        .listarTudo()).withRel(IanaLinkRelations.COLLECTION));
    }

}
