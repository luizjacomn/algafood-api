package com.luizjacomn.algafood.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { SandboxIncluiDestinatarioValidator.class })
public @interface SandboxIncluiDestinatario {

    String message() default "Sandbox deve incluir valor para destinatário";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
