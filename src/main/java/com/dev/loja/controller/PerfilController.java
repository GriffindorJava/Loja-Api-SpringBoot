package com.dev.loja.controller;

import com.dev.loja.model.Endereco;
import com.dev.loja.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@AllArgsConstructor
@RequestMapping("perfil")
public class PerfilController {
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> perfil(){
        return userService.mostrarPerfil();
    }

    @PostMapping("endereco")
    public ResponseEntity<?> adicionarEndereco(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody Endereco endereco){
        return userService.adicionarEndereco(userDetails, endereco);
    }

    @PatchMapping("endereco/{id}")
    public ResponseEntity<?> editarEndereco(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Endereco endereco, @PathVariable Long id) throws InvocationTargetException, IllegalAccessException {
        return userService.editarEndereco(userDetails, endereco, id);
    }
}
