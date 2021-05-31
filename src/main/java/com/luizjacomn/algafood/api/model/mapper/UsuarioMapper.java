package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.controller.UsuarioController;
import com.luizjacomn.algafood.api.controller.UsuarioGrupoController;
import com.luizjacomn.algafood.api.model.input.UsuarioInput;
import com.luizjacomn.algafood.api.model.output.UsuarioOutput;
import com.luizjacomn.algafood.domain.model.Usuario;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioMapper extends GenericRepresentationModelMapper<Usuario, UsuarioInput, UsuarioOutput, UsuarioController> {

    @Override
    public UsuarioOutput toModel(Usuario entity) {
        UsuarioOutput usuarioOutput = toModel(entity);

        usuarioOutput.add(linkTo(UsuarioGrupoController.class, usuarioOutput.getId()).withRel("grupos-usuario"));

        return usuarioOutput;
    }

    @Override
    public Serializable getIdentifier(UsuarioOutput output) {
        return output.getId();
    }
}
