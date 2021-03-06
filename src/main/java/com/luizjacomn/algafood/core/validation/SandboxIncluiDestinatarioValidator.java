package com.luizjacomn.algafood.core.validation;

import com.luizjacomn.algafood.core.enums.MailImpl;
import com.luizjacomn.algafood.core.mail.MailProperties;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SandboxIncluiDestinatarioValidator implements ConstraintValidator<SandboxIncluiDestinatario, MailProperties> {

    @Override
    public boolean isValid(MailProperties value, ConstraintValidatorContext context) {
        if (MailImpl.SANDBOX.equals(value.getImpl())) {
            return value.getSandbox().getDestinatario() != null && !value.getSandbox().getDestinatario().isEmpty();
        }

        return true;
    }
}
