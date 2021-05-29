package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.controller.CidadeController;
import com.luizjacomn.algafood.api.controller.EstadoController;
import com.luizjacomn.algafood.api.model.input.CidadeInput;
import com.luizjacomn.algafood.api.model.output.CidadeOutput;
import com.luizjacomn.algafood.domain.model.Cidade;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CidadeMapper extends GenericRepresentationModelMapper<Cidade, CidadeInput, CidadeOutput, CidadeController> {

    @Override
    public String[] getRelationshipAttributes() {
        return new String[] { "estado" };
    }

    @Override
    public CidadeOutput toModel(Cidade entity) {
        CidadeOutput cidadeOutput = modelMapper.map(entity, outputClass);

        cidadeOutput.add(linkTo(CidadeController.class).slash(cidadeOutput.getId()).withSelfRel());

        cidadeOutput.add(linkTo(CidadeController.class).withRel(IanaLinkRelations.COLLECTION));

        cidadeOutput.getEstado().add(linkTo(EstadoController.class).slash(cidadeOutput.getEstado().getId()).withSelfRel());

        cidadeOutput.getEstado().add(linkTo(EstadoController.class).withRel(IanaLinkRelations.COLLECTION));

        return cidadeOutput;
    }
}
