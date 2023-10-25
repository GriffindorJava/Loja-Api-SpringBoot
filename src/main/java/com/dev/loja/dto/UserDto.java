package com.dev.loja.dto;

import com.dev.loja.controller.UserController;
import com.dev.loja.enums.UserRole;
import com.dev.loja.model.User;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@NoArgsConstructor
public class UserDto extends RepresentationModel<UserDto> {
    public String usuarioEmail;
    public UserRole papel;

    public UserDto(User user){
        this.usuarioEmail = user.getLogin();
        this.papel = user.getRole();
        this.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .buscarPorId(user.getId())).withSelfRel());
        this.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .listarTudo()).withRel(IanaLinkRelations.COLLECTION));
    }
}
