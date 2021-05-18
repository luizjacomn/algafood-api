package com.luizjacomn.algafood.core.mail;

import com.luizjacomn.algafood.core.enums.MailImpl;
import com.luizjacomn.algafood.core.validation.SandboxIncluiDestinatario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@SandboxIncluiDestinatario
@Getter
@Setter
@Component
@ConfigurationProperties("api.service.mail")
public class MailProperties {

    @NotBlank
    private String from;

    private MailImpl impl = MailImpl.FAKE;

    private Sandbox sandbox = new Sandbox();

    @Getter
    @Setter
    public class Sandbox {

        private String destinatario;

    }

}
