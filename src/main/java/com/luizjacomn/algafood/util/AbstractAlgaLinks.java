package com.luizjacomn.algafood.util;

import org.springframework.hateoas.Link;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public abstract class AbstractAlgaLinks {

    public Link linkFor(Class controllerClass, String rel) {
        return linkTo(controllerClass).withRel(rel);
    }

    public Link linkFor(Class controllerClass) {
        String simpleName = controllerClass.getSimpleName();

        Pattern pattern = Pattern.compile("^[A-Z]{1}[a-z]+");
        Matcher matcher = pattern.matcher(simpleName);

        if (matcher.find()) {
            return linkTo(controllerClass).withRel(matcher.group().concat("s").toLowerCase());
        }

        throw new RuntimeException("Erro ao montar link para: " + simpleName);
    }

}
