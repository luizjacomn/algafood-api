package com.luizjacomn.algafood.domain.listener;

import com.luizjacomn.algafood.domain.event.PedidoCanceladoEvent;
import com.luizjacomn.algafood.domain.model.Pedido;
import com.luizjacomn.algafood.domain.service.mail.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoPedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        Pedido pedido = event.getPedido();

        EnvioEmailService.Mensagem mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
                .template("pedido-cancelado.html")
                .parametro("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
    }

}
