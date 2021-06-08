package com.luizjacomn.algafood.api.v1.model.mapper;

import com.luizjacomn.algafood.api.mapper.GenericRepresentationModelMapper;
import com.luizjacomn.algafood.api.v1.controller.UsuarioController;
import com.luizjacomn.algafood.api.v1.controller.UsuarioGrupoController;
import com.luizjacomn.algafood.api.v1.model.input.UsuarioInput;
import com.luizjacomn.algafood.api.v1.model.output.UsuarioOutput;
import com.luizjacomn.algafood.domain.model.Usuario;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UsuarioMapper extends GenericRepresentationModelMapper<Usuario, UsuarioInput, UsuarioOutput, UsuarioController> {

    @Override
    public UsuarioOutput toModel(Usuario entity) {
        UsuarioOutput usuarioOutput = super.toModel(entity);

        usuarioOutput.add(linkTo(UsuarioGrupoController.class, usuarioOutput.getId()).withRel("grupos-usuario"));

        return usuarioOutput;
    }

    @Override
    public Serializable getIdentifier(UsuarioOutput output) {
        return output.getId();
    }
}
