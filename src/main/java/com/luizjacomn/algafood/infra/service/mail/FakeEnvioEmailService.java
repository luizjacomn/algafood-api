package com.luizjacomn.algafood.infra.service.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FakeEnvioEmailService extends SmtpEnvioEmailService {

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            String corpo = processarTemplate(mensagem);

            log.info("Assunto: " + mensagem.getAssunto());
            log.info("Remetente: " + mailProperties.getFrom());
            log.info("Destinatário: " + mensagem.getDestinatarios().stream().findFirst().get());
            log.info("Corpo: \n" + corpo);
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail fake", e);
        }
    }

}
