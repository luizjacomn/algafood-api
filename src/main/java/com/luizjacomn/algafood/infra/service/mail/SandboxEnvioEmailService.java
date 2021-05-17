package com.luizjacomn.algafood.infra.service.mail;

import com.luizjacomn.algafood.core.mail.MailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class SandboxEnvioEmailService extends SmtpEnvioEmailService {

    @Autowired
    private MailProperties mailProperties;

    @Override
    protected void configurarDestinatario(Mensagem mensagem, MimeMessageHelper helper) throws Exception {
        helper.setTo(mailProperties.getSandbox().getDestinatario());
    }
}
