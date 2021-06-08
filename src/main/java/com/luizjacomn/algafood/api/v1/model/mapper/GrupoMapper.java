package com.luizjacomn.algafood.api.v1.model.mapper;

import com.luizjacomn.algafood.api.mapper.GenericRepresentationModelMapper;
import com.luizjacomn.algafood.api.v1.controller.GrupoController;
import com.luizjacomn.algafood.api.v1.controller.GrupoPermissaoController;
import com.luizjacomn.algafood.api.v1.model.input.GrupoInput;
import com.luizjacomn.algafood.api.v1.model.output.GrupoOutput;
import com.luizjacomn.algafood.domain.model.Grupo;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class GrupoMapper extends GenericRepresentationModelMapper<Grupo, GrupoInput, GrupoOutput, GrupoController> {

    @Override
    public GrupoOutput toModel(Grupo entity) {
        GrupoOutput grupoOutput = super.toModel(entity);

        grupoOutput.add(WebMvcLinkBuilder.linkTo(GrupoPermissaoController.class, grupoOutput.getId())
                .withRel("permissoes"));

        return grupoOutput;
    }

    @Override
    public Serializable getIdentifier(GrupoOutput output) {
        return output.getId();
    }
}
