package com.dev.loja.repository;

import com.dev.loja.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {
    Imagem findByCaminho(String caminho);
}
