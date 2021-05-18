package com.luizjacomn.algafood.infra.service.mail;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SandboxEnvioEmailService extends SmtpEnvioEmailService {

    @Override
    protected void configurarDestinatario(MimeMessageHelper helper) throws Exception {
        helper.setTo(mailProperties.getSandbox().getDestinatario());
    }
}
