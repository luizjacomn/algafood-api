package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.generics.NegocioException;
import com.luizjacomn.algafood.domain.model.Pedido;
import com.luizjacomn.algafood.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class AlteracaoStatusPedidoService {

    @Autowired
    private PedidoService pedidoService;

    @Value("${api.pedido.habilita-erro.alteracao-para-o-mesmo-status:false}")
    private boolean habilitaErroAlteracaoMesmoStatus;

    @Transactional
    public void confirmarPedido(String codigo) {
        Pedido pedido = pedidoService.buscar(codigo);

        alterarStatus(pedido, p -> !p.getStatus().equals(StatusPedido.CRIADO), StatusPedido.CONFIRMADO, pedido::setDataConfirmacao);
    }

    @Transactional
    public void entregarPedido(String codigo) {
        Pedido pedido = pedidoService.buscar(codigo);

        alterarStatus(pedido, p -> !p.getStatus().equals(StatusPedido.CONFIRMADO), StatusPedido.ENTREGUE, pedido::setDataEntrega);
    }

    @Transactional
    public void cancelarPedido(String codigo) {
        Pedido pedido = pedidoService.buscar(codigo);

        alterarStatus(pedido, p -> p.getStatus().equals(StatusPedido.CONFIRMADO) || p.getStatus().equals(StatusPedido.ENTREGUE),
                StatusPedido.CANCELADO, pedido::setDataCancelamento);
    }

    private void alterarStatus(Pedido pedido, Predicate<Pedido> lancaExcecaoSe, StatusPedido proximoStatus,
                               Consumer<OffsetDateTime> setterDataAlteracaoStatus) {

        if (pedido.getStatus().equals(proximoStatus)) {
            if (habilitaErroAlteracaoMesmoStatus) {
                throw new NegocioException(String.format("Este pedido foi %s anteriormente", proximoStatus.getDescricao().toLowerCase()));
            }
        } else {
            validar(pedido, lancaExcecaoSe, proximoStatus);

            pedido.paraProximoStatus(proximoStatus, setterDataAlteracaoStatus);
        }
    }

    private void validar(Pedido pedido, Predicate<Pedido> lancaExcecaoSe, StatusPedido proximoStatus) {
        if (lancaExcecaoSe.test(pedido)) {
            throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de '%s' para '%s'",
                    pedido.getCodigo(), pedido.getStatus().getDescricao(), proximoStatus.getDescricao()));
        }
    }

}
