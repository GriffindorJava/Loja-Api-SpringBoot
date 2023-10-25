package com.dev.loja.service;

import com.dev.loja.dto.ImagemDtoSaida;
import com.dev.loja.dto.ProdutoDtoEntrada;
import com.dev.loja.dto.ProdutoDtoSaida;
import com.dev.loja.exception.EntityNotFoundException;
import com.dev.loja.exception.FileOperationException;
import com.dev.loja.model.Imagem;
import com.dev.loja.model.Produto;
import com.dev.loja.repository.CategoriaRepository;
import com.dev.loja.repository.ImagemRepository;
import com.dev.loja.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProdutoService {
    private ProdutoRepository produtoRepository;
    private CategoriaRepository categoriaRepository;
    private ImagemRepository imagemRepository;

    private BeanUtilsBean beanUtilsBean;

//    private final String path = System.getProperty("user.dir") + "/upload/produtos/";
    private final String path = "/home/elenildo/apps/loja/imagens/produtos/";


    public ResponseEntity<?> listarTudo() {
        return new ResponseEntity<>(produtoRepository.findAll()
                .stream().map(ProdutoDtoSaida::new).toList(), HttpStatus.OK);
    }

    public ResponseEntity<?> produtosPorCategoriaNome(String categoria) {
        return new ResponseEntity<>(produtoRepository.getProdutoByCategoriaNome(categoria)
                .stream().map(ProdutoDtoSaida::new).toList(), HttpStatus.OK);
    }
    public ResponseEntity<?> novo(ProdutoDtoEntrada produtoDto) {
        var busca = categoriaRepository.findById(produtoDto.categoria.getId());
        if(busca.isEmpty())
//            return new ResponseEntity<>("A categoria informada não existe", HttpStatus.BAD_REQUEST);
            throw new EntityNotFoundException("A categoria informada não existe");

        return new ResponseEntity<>(produtoRepository.save(new Produto(produtoDto)), HttpStatus.CREATED);
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        var produto = buscarProdutoPorId(id);
        return new ResponseEntity<>(new ProdutoDtoSaida(produto), HttpStatus.OK);
    }

    public ResponseEntity<?> adicionarImagens(Long id, MultipartFile[] files) {
        var produto = buscarProdutoPorId(id);

        String filename;
        List<Imagem> imagens = produto.getImagens();
        Path fileNameAndPath;

        for(MultipartFile file : files){
            filename = UUID.randomUUID().toString();
//            filename = file.getOriginalFilename();
            fileNameAndPath = Paths.get(this.path, filename);
            Imagem imagem = new Imagem();
            imagem.setCaminho(fileNameAndPath.toString());
            imagem.setProduto(produto);
            imagens.add(imagem);
            try {
                Files.write(fileNameAndPath, file.getBytes());;
            } catch (IOException e) {
                throw new FileOperationException("Erro ao fazer upload: "+e.getMessage());
            }
        }

        produto.setImagens(imagemRepository.saveAll(imagens));
        produtoRepository.save(produto);

        return new ResponseEntity<>(new ProdutoDtoSaida(produto), HttpStatus.OK);
    }

    public ResponseEntity<?> removerImagens(Long produtoId, List<ImagemDtoSaida> imagens) {
        Imagem imagem;
        File arquivo;
        for(ImagemDtoSaida imagemDto : imagens){
            imagem = imagemRepository.findByCaminho(imagemDto.caminho);
            if(imagem != null){
                arquivo = new File(imagemDto.caminho);
                if(arquivo.delete())
                    imagemRepository.delete(imagem);
            }
        }
        return buscarPorId(produtoId);
    }

    public ResponseEntity<?> buscarPorNome(String nome) {
        return new ResponseEntity<>(produtoRepository.findByNomeContainingIgnoreCase(nome)
                .stream().map(ProdutoDtoSaida::new).toList(), HttpStatus.OK);
    }

    public ResponseEntity<?> editar(ProdutoDtoEntrada produto, Long id) throws InvocationTargetException, IllegalAccessException {
        var prod = buscarProdutoPorId(id);
        beanUtilsBean.copyProperties(prod, new Produto(produto));

        return new ResponseEntity<>(new ProdutoDtoSaida(produtoRepository.save(prod)), HttpStatus.OK);
    }

    private Produto buscarProdutoPorId(Long id) {
        var busca = produtoRepository.findById(id);
        if (busca.isEmpty())
            throw new EntityNotFoundException("Produto não encontrado");

        return busca.get();
    }

}
