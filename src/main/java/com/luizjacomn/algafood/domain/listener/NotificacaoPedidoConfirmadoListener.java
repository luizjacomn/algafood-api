package com.luizjacomn.algafood.domain.listener;

import com.luizjacomn.algafood.domain.event.PedidoConfirmadoEvent;
import com.luizjacomn.algafood.domain.model.Pedido;
import com.luizjacomn.algafood.domain.service.mail.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoPedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @EventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();

        EnvioEmailService.Mensagem mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .template("pedido-confirmado.html")
                .parametro("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
    }

}
