package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.controller.EstadoController;
import com.luizjacomn.algafood.api.model.input.EstadoInput;
import com.luizjacomn.algafood.api.model.output.EstadoOutput;
import com.luizjacomn.algafood.domain.model.Estado;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class EstadoMapper extends GenericRepresentationModelMapper<Estado, EstadoInput, EstadoOutput, EstadoController> {

    @Override
    public Serializable getIdentifier(EstadoOutput output) {
        return output.getId();
    }
}
