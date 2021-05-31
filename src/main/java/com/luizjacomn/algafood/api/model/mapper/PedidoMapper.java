package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.controller.*;
import com.luizjacomn.algafood.api.model.input.PedidoInput;
import com.luizjacomn.algafood.api.model.output.PedidoOutput;
import com.luizjacomn.algafood.domain.model.Pedido;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PedidoMapper extends GenericRepresentationModelMapper<Pedido, PedidoInput, PedidoOutput, PedidoController> {

    @Override
    public PedidoOutput toModel(Pedido entity) {
        PedidoOutput pedidoOutput = super.toModel(entity);

        pedidoOutput.getRestaurante().add(linkTo(RestauranteController.class)
                .slash(pedidoOutput.getRestaurante().getId()).withSelfRel());

        pedidoOutput.getRestaurante().add(linkTo(RestauranteController.class).withRel(IanaLinkRelations.COLLECTION));

        pedidoOutput.getCliente().add(linkTo(UsuarioController.class).slash(pedidoOutput.getCliente().getId()).withSelfRel());

        pedidoOutput.getCliente().add(linkTo(UsuarioController.class).withRel(IanaLinkRelations.COLLECTION));

        pedidoOutput.getFormaPagamento().add(linkTo(FormaPagamentoController.class)
                .slash(pedidoOutput.getFormaPagamento().getId()).withSelfRel());

        pedidoOutput.getFormaPagamento().add(linkTo(FormaPagamentoController.class).withRel(IanaLinkRelations.COLLECTION));

        pedidoOutput.getEnderecoEntrega().getCidade().add(linkTo(CidadeController.class)
                .slash(pedidoOutput.getEnderecoEntrega().getCidade().getId()).withSelfRel());

        pedidoOutput.getEnderecoEntrega().getCidade().add(linkTo(CidadeController.class).withRel(IanaLinkRelations.COLLECTION));

        pedidoOutput.getItens().forEach(item -> {
            item.add(linkTo(RestauranteProdutoController.class, pedidoOutput.getRestaurante().getId())
                    .slash(item.getProdutoId())
                    .withRel("produto"));
        });

        return pedidoOutput;
    }

    @Override
    public Serializable getIdentifier(PedidoOutput output) {
        return output.getCodigo();
    }
}
