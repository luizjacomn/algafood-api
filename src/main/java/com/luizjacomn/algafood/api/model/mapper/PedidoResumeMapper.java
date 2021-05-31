package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.controller.EstadoController;
import com.luizjacomn.algafood.api.controller.PedidoController;
import com.luizjacomn.algafood.api.controller.RestauranteController;
import com.luizjacomn.algafood.api.controller.UsuarioController;
import com.luizjacomn.algafood.api.model.input.PedidoInput;
import com.luizjacomn.algafood.api.model.output.PedidoResumeOutput;
import com.luizjacomn.algafood.domain.model.Pedido;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PedidoResumeMapper extends GenericRepresentationModelMapper<Pedido, PedidoInput, PedidoResumeOutput, PedidoController> {

    @Override
    public PedidoResumeOutput toModel(Pedido entity) {
        PedidoResumeOutput pedidoResumeOutput = super.toModel(entity);

        pedidoResumeOutput.getRestaurante().add(linkTo(RestauranteController.class).slash(pedidoResumeOutput.getRestaurante().getId()).withSelfRel());

        pedidoResumeOutput.getRestaurante().add(linkTo(RestauranteController.class).withRel(IanaLinkRelations.COLLECTION));

        pedidoResumeOutput.getCliente().add(linkTo(UsuarioController.class).slash(pedidoResumeOutput.getCliente().getId()).withSelfRel());

        pedidoResumeOutput.getCliente().add(linkTo(UsuarioController.class).withRel(IanaLinkRelations.COLLECTION));

        return pedidoResumeOutput;
    }

    @Override
    public Serializable getIdentifier(PedidoResumeOutput output) {
        return output.getCodigo();
    }
}
