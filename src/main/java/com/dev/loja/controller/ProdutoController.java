package com.dev.loja.controller;

import com.dev.loja.dto.ImagemDtoSaida;
import com.dev.loja.dto.ProdutoDtoEntrada;
import com.dev.loja.model.Produto;
import com.dev.loja.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("admin/produtos")
public class ProdutoController {
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<?> listarTudo(){
        return produtoService.listarTudo();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        return produtoService.buscarPorId(id);
    }

    @PostMapping("busca")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome){
        return produtoService.buscarPorNome(nome);
    }

    @PostMapping
    public ResponseEntity<?> novo(@RequestBody @Valid ProdutoDtoEntrada produtoDto){
        return produtoService.novo(produtoDto);
    }

    @PostMapping("{produtoId}/imagens")
    public ResponseEntity<?> adicionarImagens(@PathVariable Long produtoId, @RequestParam MultipartFile[] files){
        return produtoService.adicionarImagens(produtoId, files);
    }

    @DeleteMapping("{produtoId}/imagens")
    public ResponseEntity<?> removerImagens(@PathVariable Long produtoId, @RequestBody List<ImagemDtoSaida> imagens){
        return produtoService.removerImagens(produtoId, imagens);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> editar(@RequestBody ProdutoDtoEntrada produto, @PathVariable Long id) throws InvocationTargetException, IllegalAccessException {
        return produtoService.editar(produto, id);
    }
}
