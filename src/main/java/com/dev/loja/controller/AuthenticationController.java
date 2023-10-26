package com.dev.loja.controller;

import com.dev.loja.dto.AuthenticationDTO;
import com.dev.loja.dto.RegisterDTO;
import com.dev.loja.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        return authenticationService.login(data);
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        return authenticationService.register(data);
    }

}
