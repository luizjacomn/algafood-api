package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.generics.NegocioException;
import com.luizjacomn.algafood.domain.model.Pedido;
import com.luizjacomn.algafood.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Predicate;

@Service
public class AlteracaoStatusPedidoService {

    @Autowired
    private PedidoService pedidoService;

    @Transactional
    public void confirmarPedido(Long id) {
        Pedido pedido = pedidoService.buscar(id);

        validar(pedido, p -> !p.getStatus().equals(StatusPedido.CRIADO), StatusPedido.CONFIRMADO);

        pedido.paraProximoStatus(StatusPedido.CONFIRMADO, pedido::setDataConfirmacao);
    }

    @Transactional
    public void entregarPedido(Long id) {
        Pedido pedido = pedidoService.buscar(id);

        validar(pedido, p -> !p.getStatus().equals(StatusPedido.CONFIRMADO), StatusPedido.ENTREGUE);

        pedido.paraProximoStatus(StatusPedido.ENTREGUE, pedido::setDataEntrega);
    }

    @Transactional
    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoService.buscar(id);

        validar(pedido, p -> p.getStatus().equals(StatusPedido.CONFIRMADO) || p.getStatus().equals(StatusPedido.ENTREGUE), StatusPedido.CANCELADO);

        pedido.paraProximoStatus(StatusPedido.CANCELADO, pedido::setDataCancelamento);
    }

    private void validar(Pedido pedido, Predicate<Pedido> lancaExcecaoSe, StatusPedido proximoStatus) {
        if (lancaExcecaoSe.test(pedido)) {
            throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de '%s' para '%s'",
                    pedido.getId(), pedido.getStatus().getDescricao(), proximoStatus.getDescricao()));
        }
    }

}
