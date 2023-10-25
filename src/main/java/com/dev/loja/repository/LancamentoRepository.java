package com.dev.loja.repository;

import com.dev.loja.model.Lancamento;
import com.dev.loja.enums.ProdutoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    List<Lancamento> findByProdutoId(Long produtoId);

    @Query("SELECT count(l) FROM Lancamento l " +
            "WHERE l.produto.id = :produtoId " +
            "AND l.produtoStatus = ProdutoStatus.DISPONIVEL")
    int consultarEstoque(Long produtoId);

    @Query("SELECT l FROM Lancamento l " +
            "WHERE l.produto.id = :produtoId " +
            "AND l.produtoStatus = :produtoStatus " +
            "ORDER BY l.id " +
            "LIMIT 1")
    Lancamento retornarPrimeirolancamento(Long produtoId, ProdutoStatus produtoStatus);
}
