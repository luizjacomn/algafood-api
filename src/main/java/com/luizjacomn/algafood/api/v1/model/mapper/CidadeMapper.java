package com.luizjacomn.algafood.api.v1.model.mapper;

import com.luizjacomn.algafood.api.mapper.GenericRepresentationModelMapper;
import com.luizjacomn.algafood.api.v1.controller.CidadeController;
import com.luizjacomn.algafood.api.v1.controller.EstadoController;
import com.luizjacomn.algafood.api.v1.model.input.CidadeInput;
import com.luizjacomn.algafood.api.v1.model.output.CidadeOutput;
import com.luizjacomn.algafood.domain.model.Cidade;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CidadeMapper extends GenericRepresentationModelMapper<Cidade, CidadeInput, CidadeOutput, CidadeController> {

    @Override
    public String[] getRelationshipAttributes() {
        return new String[] { "estado" };
    }

    @Override
    public CidadeOutput toModel(Cidade entity) {
        CidadeOutput cidadeOutput = super.toModel(entity);

        cidadeOutput.getEstado().add(linkTo(EstadoController.class).slash(cidadeOutput.getEstado().getId()).withSelfRel());

        cidadeOutput.getEstado().add(linkTo(EstadoController.class).withRel(IanaLinkRelations.COLLECTION));

        return cidadeOutput;
    }

    @Override
    public Serializable getIdentifier(CidadeOutput output) {
        return output.getId();
    }
}
