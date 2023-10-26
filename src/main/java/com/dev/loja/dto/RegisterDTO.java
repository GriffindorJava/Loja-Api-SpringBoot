package com.dev.loja.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank String nome,
        @NotBlank String sobrenome,
        @Email String login,
        @NotBlank String password) {
}
