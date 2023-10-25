package com.dev.loja.repository;

import com.dev.loja.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p WHERE p.categoria.nome = :categoria")
    List<Produto> getProdutoByCategoriaNome(String categoria);

    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
