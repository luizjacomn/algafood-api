package com.luizjacomn.algafood.util;

import com.luizjacomn.algafood.api.controller.RestauranteController;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class AlgaLinks {

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
