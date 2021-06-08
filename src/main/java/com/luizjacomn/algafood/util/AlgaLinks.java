package com.luizjacomn.algafood.util;

import com.luizjacomn.algafood.api.v1.controller.relatorios.PedidoRelatoriosController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    public Link linkForRelatorioVendasDiarias() {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("timeOffset", TemplateVariable.VariableType.REQUEST_PARAM));

        String pedidosUrl = linkTo(methodOn(PedidoRelatoriosController.class)
                .pesquisar(null, null)).toUri().toString();

        return Link.of(UriTemplate.of(pedidosUrl, filtroVariables), "vendas-diarias");
    }

}
