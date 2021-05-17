package com.luizjacomn.algafood.infra.service.mail;

import com.luizjacomn.algafood.core.mail.MailProperties;
import com.luizjacomn.algafood.domain.model.Pedido;
import com.luizjacomn.algafood.domain.service.mail.EnvioEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FakeEnvioEmailService implements EnvioEmailService {

    @Autowired
    private MailProperties mailProperties;

    @Override
    public void enviar(Mensagem mensagem) {
        Pedido pedido = (Pedido) mensagem.getParametros().get("pedido");
        String corpo = String.format("Caro(a) %s, o pedido de código %s foi confirmado pelo restaurante.",
                                    pedido.getCliente().getNome(), pedido.getCodigo());

        log.info("Assunto: " + mensagem.getAssunto());
        log.info("Remetente: " + mailProperties.getFrom());
        log.info("Destinatário: " + mensagem.getDestinatarios().stream().findFirst().get());
        log.info("Corpo: " + corpo);
    }
}
