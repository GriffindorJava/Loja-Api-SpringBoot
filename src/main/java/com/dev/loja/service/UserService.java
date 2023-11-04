package com.dev.loja.service;

import com.dev.loja.dto.*;
import com.dev.loja.exception.BadRequestException;
import com.dev.loja.exception.EntityNotFoundException;
import com.dev.loja.model.Endereco;
import com.dev.loja.model.Produto;
import com.dev.loja.model.User;
import com.dev.loja.repository.EnderecoRepository;
import com.dev.loja.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private EnderecoRepository enderecoRepository;
    private BeanUtilsBean beanUtilsBean;

    public ResponseEntity<?> listarTudo() {
        return new ResponseEntity<>(userRepository.findAll().stream().map(UserDto::new), HttpStatus.OK);
    }

    public ResponseEntity<?> adicionarEndereco(UserDetails userDetails, Endereco endereco) {
        var user = buscarUsuarioPorLogin(userDetails.getUsername());

        if(endereco.getPrincipal()) enderecoRepository.desativarEnderecos(user.getId());

        endereco.setUser(user);
        return new ResponseEntity<>(new EnderecoDtoSaida(enderecoRepository.save(endereco)), HttpStatus.OK);
    }

    public ResponseEntity<?> editarEndereco(UserDetails userDetails, Endereco endereco, Long id) throws InvocationTargetException, IllegalAccessException {
        var user = buscarUsuarioPorLogin(userDetails.getUsername());
        var end = buscarEnderecoPorId(id);

        if(!Objects.equals(end.getUser(), user))
            throw new BadRequestException("O endereço não pertence ao usuário logado");

        if(endereco.getPrincipal()) enderecoRepository.desativarEnderecos(user.getId());

        beanUtilsBean.copyProperties(end, endereco);
        return new ResponseEntity<>(new EnderecoDtoSaida(enderecoRepository.save(end)), HttpStatus.OK);
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        var busca = userRepository.findById(id);
        if(busca.isEmpty())
           throw new EntityNotFoundException("Usuário não encontrado");

        return new ResponseEntity<>(new UserDtoSaida(busca.get()), HttpStatus.OK);
    }

    public ResponseEntity<?> alterarUser(UserDto user) {
        var usuario = buscarUsuarioPorLogin(user.usuarioEmail);

        usuario.setRole(user.papel);
        return new ResponseEntity<>(new UserDtoSaida(userRepository.save(usuario)), HttpStatus.OK);
    }

    private User buscarUsuarioPorLogin(String login) {
        var busca = userRepository.findUserByLogin(login);
        if (busca.isEmpty())
            throw new EntityNotFoundException("Usuário não encontrado");
        return busca.get();
    }

    private Endereco buscarEnderecoPorId(Long id) {
        var busca = enderecoRepository.findById(id);
        if (busca.isEmpty())
            throw new EntityNotFoundException("Endereço não encontrado");
        return busca.get();
    }

    public ResponseEntity<?> mostrarPerfil() {
        return new ResponseEntity<>(SecurityContextHolder.getContext().getAuthentication().getName(), HttpStatus.OK);
    }
}
