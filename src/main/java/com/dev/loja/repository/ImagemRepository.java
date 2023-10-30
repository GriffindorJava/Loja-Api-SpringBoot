package com.dev.loja.repository;

import com.dev.loja.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {
    Imagem findByCaminho(String caminho);
    List<Imagem> findByProdutoId(Long id);
    Imagem findFirstByProdutoId(Long id);
}
