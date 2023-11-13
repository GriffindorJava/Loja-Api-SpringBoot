package com.dev.loja.dto;

import com.dev.loja.controller.CategoriaController;
import com.dev.loja.model.Categoria;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@NoArgsConstructor
public class CategoriaDtoSaida extends RepresentationModel<CategoriaDtoSaida> {
    public Long id;
    public String nome;

    public CategoriaDtoSaida(Categoria categoria){
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoriaController.class)
                .buscarPorId(categoria.getId())).withSelfRel());
        this.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoriaController.class)
                .listarTudo()).withRel(IanaLinkRelations.COLLECTION));
    }
}
