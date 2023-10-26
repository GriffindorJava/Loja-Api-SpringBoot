package com.dev.loja.controller;

import com.dev.loja.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class VitrineController {
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<?> index(){
        return home();
    }
    @GetMapping("home")
    public ResponseEntity<?> home(){
        return produtoService.listarTudoVitrine();
    }
    @GetMapping("{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        return produtoService.buscarPorIdHome(id);
    }
    @GetMapping("home/{categoria}")
    public ResponseEntity<?> produtosPorCategoria(@PathVariable String categoria){
        return produtoService.produtosPorCategoriaNome(categoria);
    }

}
