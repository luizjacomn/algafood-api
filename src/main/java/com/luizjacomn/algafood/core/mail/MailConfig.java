package com.luizjacomn.algafood.core.mail;

import com.luizjacomn.algafood.core.enums.MailImpl;
import com.luizjacomn.algafood.domain.service.mail.EnvioEmailService;
import com.luizjacomn.algafood.infra.service.mail.FakeEnvioEmailService;
import com.luizjacomn.algafood.infra.service.mail.SandboxEnvioEmailService;
import com.luizjacomn.algafood.infra.service.mail.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

    @Autowired
    private MailProperties mailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        switch (mailProperties.getImpl()) {
            case SMTP:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandboxEnvioEmailService();
            case FAKE:
                return new FakeEnvioEmailService();
            default:
                return null;
        }
    }
}
