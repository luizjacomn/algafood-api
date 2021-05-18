package com.luizjacomn.algafood.domain.listener;

import com.luizjacomn.algafood.domain.event.PedidoConfirmadoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdicionarPontuacaoPedidoConfirmadoListener {

    @EventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        log.info("Adicionando pontuação na carteira do cliente: " + event.getPedido().getCliente().getNome());
    }
}
