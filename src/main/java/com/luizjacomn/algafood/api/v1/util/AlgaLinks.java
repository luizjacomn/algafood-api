package com.luizjacomn.algafood.api.v1.util;

import com.luizjacomn.algafood.api.v1.controller.relatorios.PedidoRelatoriosController;
import com.luizjacomn.algafood.util.AbstractAlgaLinks;
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
public class AlgaLinks extends AbstractAlgaLinks {

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
