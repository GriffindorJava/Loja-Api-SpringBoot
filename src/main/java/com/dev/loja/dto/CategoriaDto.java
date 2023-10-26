package com.dev.loja.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDto(@NotBlank String nome) {

}
