package com.dev.loja.controller;

import com.dev.loja.dto.CarrinhoDto;
import com.dev.loja.service.VendaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("carrinho")
public class VendaController {
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<?> fecharPedido(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestBody @Valid CarrinhoDto carrinho){
        return vendaService.fecharPedido(carrinho, userDetails);
    }

}
