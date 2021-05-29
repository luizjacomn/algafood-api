package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.controller.EstadoController;
import com.luizjacomn.algafood.api.model.input.EstadoInput;
import com.luizjacomn.algafood.api.model.output.EstadoOutput;
import com.luizjacomn.algafood.domain.model.Estado;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class EstadoMapper extends GenericRepresentationModelMapper<Estado, EstadoInput, EstadoOutput, EstadoController> {

    @Override
    public EstadoOutput toModel(Estado entity) {
        EstadoOutput estadoOutput = modelMapper.map(entity, outputClass);

        estadoOutput.add(linkTo(EstadoController.class).slash(estadoOutput.getId()).withSelfRel());

        estadoOutput.add(linkTo(EstadoController.class).withRel(IanaLinkRelations.COLLECTION));

        return estadoOutput;
    }
}
