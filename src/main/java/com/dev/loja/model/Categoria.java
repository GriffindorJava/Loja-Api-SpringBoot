package com.dev.loja.model;

import com.dev.loja.dto.CategoriaDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String nome;

    public Categoria(CategoriaDto categoriaDto){
        this.nome = categoriaDto.nome().trim();
    }

}
