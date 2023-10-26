package com.dev.loja.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(@Email String login, @NotBlank String password) {
}
