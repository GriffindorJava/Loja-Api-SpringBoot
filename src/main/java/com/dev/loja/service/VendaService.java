package com.dev.loja.service;

import com.dev.loja.beans.Util;
import com.dev.loja.dto.CarrinhoDto;
import com.dev.loja.dto.CarrinhoItem;
import com.dev.loja.dto.PedidoDtoSaida;
import com.dev.loja.enums.PedidoStatus;
import com.dev.loja.enums.ProdutoStatus;
import com.dev.loja.exception.BadRequestException;
import com.dev.loja.exception.EntityNotFoundException;
import com.dev.loja.model.ItemPedido;
import com.dev.loja.model.Lancamento;
import com.dev.loja.model.Pedido;
import com.dev.loja.model.Produto;
import com.dev.loja.repository.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class VendaService {
    private VendaRepository vendaRepository;
    private UserRepository userRepository;
    private ItemPedidoRepository itemPedidoRepository;
    private ProdutoRepository produtoRepository;
    private LancamentoRepository lancamentoRepository;


    public ResponseEntity<?> fecharPedido(@Valid CarrinhoDto carrinho, UserDetails userDetails) {

        if(carrinho.itens().isEmpty())
            throw new BadRequestException("Nenhum item foi adicionado ao carrinho");

        Pedido pedido = new Pedido();
        pedido.setUser(null);
        pedido.setCep(carrinho.cep());

        if(userDetails != null){
            var busca = userRepository.findUserByLogin(userDetails.getUsername());

            if(busca.isEmpty())
                throw new EntityNotFoundException("Cliente não encontrado");

            if(busca.get().getEnderecos().isEmpty())
                throw new BadRequestException("O cliente não tem nenhum endereço cadastrado");

            pedido.setUser(busca.get());
        }

        pedido.setFormaPagamento(carrinho.formaPagamento());
        pedido.setData(LocalDateTime.now());

        pedido.setFrete(Util.calculaFrete("17548700", carrinho.cep()));//implementar

        if(userDetails != null) {
            vendaRepository.save(pedido);
        }
        return this.adicionarItens(pedido, carrinho.itens());
    }

    private ResponseEntity<?> adicionarItens(Pedido pedido, List<CarrinhoItem> itens) {
        BigDecimal totalPedido = BigDecimal.ZERO;
        var itensDuplicadosRemovidos = this.somaRepetidos(itens);
        if(itensDuplicadosRemovidos.isEmpty())
            throw new BadRequestException("A lista de itens está vazia.");

        List<ItemPedido> itensPedido = new ArrayList<>();

        for(CarrinhoItem carrinhoItem : itensDuplicadosRemovidos){
            if(lancamentoRepository.consultarEstoque(carrinhoItem.produtoId) < carrinhoItem.quantidade){
                if(pedido.getNumero() !=null) vendaRepository.delete(pedido);
                throw new BadRequestException("Estoque insuficiente para o produto de id "+
                        carrinhoItem.produtoId);
            }
            ItemPedido itemPedido = new ItemPedido();
            Produto produto = this.getProduto(carrinhoItem.produtoId);

            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(carrinhoItem.quantidade);
            itemPedido.setSubtotal(produto.getPrecoVenda()
                            .multiply(new BigDecimal(itemPedido.getQuantidade())));
            totalPedido = totalPedido.add(itemPedido.getSubtotal());//Acumula o subtotal
            itensPedido.add(itemPedido);
            if(pedido.getNumero() !=null)
                this.atualizarEstoque(carrinhoItem);
        }

        if(pedido.getNumero() !=null)
            pedido.setItens(itemPedidoRepository.saveAll(itensPedido));
        else
            pedido.setItens(itensPedido);

        pedido.setSubtotal(totalPedido);
        pedido.setTotal(totalPedido);
        BigDecimal descontos = this.calculaDesconto(pedido);
        pedido.setDescontos(descontos);
        totalPedido = totalPedido.add(pedido.getFrete()).subtract(descontos);
        pedido.setTotal(totalPedido);
        pedido.setPedidoStatus(PedidoStatus.FECHADO);

        if(pedido.getNumero() !=null){
            var pedidoSalvo = vendaRepository.save(pedido);
            return new ResponseEntity<>(new PedidoDtoSaida(pedidoSalvo), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new PedidoDtoSaida(pedido), HttpStatus.CREATED);


    }

    private Set<CarrinhoItem> somaRepetidos(List<CarrinhoItem> itens) {
        CarrinhoItem itemAtual;
        Set<CarrinhoItem> novaLista= new HashSet<>();

        while (!itens.isEmpty()){
            itemAtual = itens.get(0);
            itens.remove(0);
            if(itemAtual.quantidade < 1) continue;

            for(CarrinhoItem item : itens){
                if(Objects.equals(itemAtual.produtoId, item.produtoId) && item.quantidade > 0){
                    itemAtual.quantidade += item.quantidade;
                }
            }
            novaLista.add(itemAtual);
        }
        return novaLista;
    }

    private BigDecimal calculaDesconto(Pedido pedido) {
        switch (pedido.getFormaPagamento()){
            case DEBITO -> {
                return new BigDecimal("0.05")
                        .multiply(pedido.getTotal())
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN);}
            case PIX -> {
                return new BigDecimal("0.10")
                        .multiply(pedido.getTotal())
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN);}
            case MERCADO_PAGO -> {
                return new BigDecimal("0.03")
                        .multiply(pedido.getTotal())
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN);}
            default -> {return BigDecimal.ZERO;}
        }
    }

    private void atualizarEstoque(CarrinhoItem item) {
        for (int i=0; i<item.quantidade; i++){
            Lancamento primeiroProduto = lancamentoRepository.retornarPrimeirolancamento(item.produtoId, ProdutoStatus.DISPONIVEL);
            primeiroProduto.setProdutoStatus(ProdutoStatus.RESERVADO);
            primeiroProduto.setDataSaida(LocalDateTime.now());
            lancamentoRepository.save(primeiroProduto);
        }
        Produto produto = this.getProduto(item.produtoId);
        produto.setEstoqueAtual(produto.getEstoqueAtual() - item.quantidade);
        produtoRepository.save(produto);
    }

    private Produto getProduto(Long id){
        return produtoRepository.findById(id).get();
    }

}
