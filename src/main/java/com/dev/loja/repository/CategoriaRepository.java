package com.dev.loja.repository;

import com.dev.loja.model.Categoria;
import com.dev.loja.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNome(String nome);
    List<Categoria> findByNomeContaining(String nome);
}
