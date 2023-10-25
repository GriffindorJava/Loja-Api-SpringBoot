package com.dev.loja.repository;

import com.dev.loja.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Pedido, Long> {
}
