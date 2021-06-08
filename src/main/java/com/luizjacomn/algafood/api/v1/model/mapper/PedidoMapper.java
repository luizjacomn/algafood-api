package com.luizjacomn.algafood.api.v1.model.mapper;

import com.luizjacomn.algafood.api.mapper.GenericRepresentationModelMapper;
import com.luizjacomn.algafood.api.v1.controller.*;
import com.luizjacomn.algafood.api.v1.model.input.PedidoInput;
import com.luizjacomn.algafood.api.v1.model.output.PedidoOutput;
import com.luizjacomn.algafood.domain.model.Pedido;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PedidoMapper extends GenericRepresentationModelMapper<Pedido, PedidoInput, PedidoOutput, PedidoController> {

    public PedidoMapper() {
        addFilterTemplateVariable("clienteId", VariableType.REQUEST_PARAM);
        addFilterTemplateVariable("restauranteId", VariableType.REQUEST_PARAM);
        addFilterTemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM);
        addFilterTemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM);
        addFilterTemplateVariable("campos", VariableType.REQUEST_PARAM);
    }

    @Override
    public PedidoOutput toModel(Pedido entity) {
        PedidoOutput pedidoOutput = super.toModel(entity);

        if (entity.podeSerConfirmado()) {
            pedidoOutput.add(linkTo(AlteracaoStatusPedidoController.class, pedidoOutput.getCodigo())
                    .slash("/confirmacao").withRel("confirmar"));
        }

        if (entity.podeSerEntregue()) {
            pedidoOutput.add(linkTo(AlteracaoStatusPedidoController.class, pedidoOutput.getCodigo())
                    .slash("/entrega").withRel("entregar"));
        }

        if (entity.podeSerCancelado()) {
            pedidoOutput.add(linkTo(AlteracaoStatusPedidoController.class, pedidoOutput.getCodigo())
                    .slash("/cancelamento").withRel("cancelar"));
        }

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
    protected boolean hasCollectionUriTemplate() {
        return true;
    }

    @Override
    protected boolean hasSort() {
        return true;
    }

    @Override
    public Serializable getIdentifier(PedidoOutput output) {
        return output.getCodigo();
    }
}
