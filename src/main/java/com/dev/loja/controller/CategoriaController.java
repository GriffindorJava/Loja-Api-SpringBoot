package com.dev.loja.controller;

import com.dev.loja.dto.CategoriaDto;
import com.dev.loja.model.Categoria;
import com.dev.loja.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/categorias")
@AllArgsConstructor
public class CategoriaController {
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<?> listarTudo(){
        return categoriaService.listarTudo();
    }

    @PostMapping
    public ResponseEntity<?> novo(@RequestBody @Valid CategoriaDto categoriaDto){
        return categoriaService.novo(categoriaDto);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody @Valid CategoriaDto categoriaDto){
        return categoriaService.editar(id, categoriaDto);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        return categoriaService.buscarPorId(id);
    }
    @PostMapping("busca")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome){
        return categoriaService.buscarCategoriaPorNome(nome);
    }
}
