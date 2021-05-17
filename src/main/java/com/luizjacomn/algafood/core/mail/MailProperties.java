package com.luizjacomn.algafood.core.mail;

import com.luizjacomn.algafood.core.enums.MailImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("api.service.mail")
public class MailProperties {

    @NotBlank
    private String from;

    private MailImpl impl = MailImpl.FAKE;

}
