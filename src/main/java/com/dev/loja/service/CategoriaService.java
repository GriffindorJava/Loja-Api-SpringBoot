package com.dev.loja.service;

import com.dev.loja.dto.CategoriaDto;
import com.dev.loja.dto.CategoriaDtoSaida;
import com.dev.loja.exception.DuplicatedEntityException;
import com.dev.loja.exception.EntityNotFoundException;
import com.dev.loja.model.Categoria;
import com.dev.loja.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoriaService {
    private CategoriaRepository categoriaRepository;

    public ResponseEntity<?> listarTudo() {
        return new ResponseEntity<>(categoriaRepository.findAll().stream().map(CategoriaDtoSaida::new), HttpStatus.OK);
    }

    public ResponseEntity<?> novo(CategoriaDto categoriaDto) {
            var busca = categoriaRepository.findByNome(categoriaDto.nome().trim());
            if(busca.isPresent())
                throw new DuplicatedEntityException("Erro: Já existe a categoria '"+categoriaDto.nome()+"'");
            var categ = categoriaRepository.save(new Categoria(categoriaDto));

        return new ResponseEntity<>(new CategoriaDtoSaida(categ), HttpStatus.CREATED);
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        var categoria = buscarCategoriaPorId(id);
        return new ResponseEntity<>(new CategoriaDtoSaida(categoria), HttpStatus.OK);
    }

    public ResponseEntity<?> editar(Long id, CategoriaDto categoriaDto) {
        var categoria = buscarCategoriaPorId(id);
        categoria.setNome(categoriaDto.nome());
        categoriaRepository.save(categoria);
        return new ResponseEntity<>(new CategoriaDtoSaida(categoria), HttpStatus.OK);
    }

    private Categoria buscarCategoriaPorId(Long id){
        var busca = categoriaRepository.findById(id);
        if(busca.isEmpty())
            throw new EntityNotFoundException("Recurso não encontrado");

        return busca.get();
    }

    public ResponseEntity<?> buscarCategoriaPorNome(String nome) {
        return new ResponseEntity<>(categoriaRepository.findByNomeContaining(nome), HttpStatus.OK);
    }
}
