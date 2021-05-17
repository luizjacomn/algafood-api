package com.luizjacomn.algafood.infra.service.mail;

import com.luizjacomn.algafood.core.mail.MailProperties;
import com.luizjacomn.algafood.domain.service.mail.EnvioEmailService;
import com.luizjacomn.algafood.infra.exception.MailException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            String template = processarTemplate(mensagem);

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(mailProperties.getFrom());
            configurarDestinatario(mensagem, helper);
            helper.setSubject(mensagem.getAssunto());
            helper.setText(template, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new MailException("Erro ao enviar e-mail", e);
        }
    }

    protected void configurarDestinatario(Mensagem mensagem, MimeMessageHelper helper) throws Exception {
        helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
    }

    private String processarTemplate(Mensagem mensagem) throws Exception {
        Template template = freemarkerConfig.getTemplate(mensagem.getTemplate());

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getParametros());
    }

}
