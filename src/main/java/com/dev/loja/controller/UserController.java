package com.dev.loja.controller;

import com.dev.loja.dto.UserDto;
import com.dev.loja.model.Endereco;
import com.dev.loja.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("admin/usuarios")
public class UserController {
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> listarTudo(){
        return userService.listarTudo();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        return userService.buscarPorId(id);
    }

    @PostMapping("roles")
    public ResponseEntity<?> alterarUser(@RequestBody UserDto userDto){
        return userService.alterarUser(userDto);
    }
}
