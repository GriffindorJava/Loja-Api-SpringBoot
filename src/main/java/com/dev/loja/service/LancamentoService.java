package com.dev.loja.service;

import com.dev.loja.exception.EntityNotFoundException;
import com.dev.loja.model.Lancamento;
import com.dev.loja.model.Produto;
import com.dev.loja.repository.LancamentoRepository;
import com.dev.loja.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LancamentoService {
    private LancamentoRepository lancamentoRepository;
    private ProdutoRepository produtoRepository;

    public ResponseEntity<?> lancarProduto(Lancamento lancamento) {
        var produto = buscarProdutoPorId(lancamento.getProduto().getId());

        if(lancamento.getQuantidade() > 0 ){

            List<Lancamento> lancamentos = new ArrayList<>();

            for(int i=0; i < lancamento.getQuantidade(); i++){
                Lancamento lanc = new Lancamento();
                lanc.setProduto(lancamento.getProduto());
                lanc.setDataLancamento(LocalDateTime.now());
                lanc.setCodigoBarras(lancamento.getCodigoBarras());
                lancamentos.add(lanc);
                produto.setEstoqueAtual(produto.getEstoqueAtual() + 1);
            }
            lancamentoRepository.saveAll(lancamentos);
            produtoRepository.save(produto);

        }
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    public ResponseEntity<?> consultarEstoque(Long produtoId) {
        var produto = buscarProdutoPorId(produtoId);
        return new ResponseEntity<>(lancamentoRepository.consultarEstoque(produtoId), HttpStatus.OK);
    }

    private Produto buscarProdutoPorId(Long id) {
        var busca = produtoRepository.findById(id);
        if (busca.isEmpty())
            throw new EntityNotFoundException("Produto não encontrado");

        return busca.get();
    }

}
