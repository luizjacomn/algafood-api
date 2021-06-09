package com.luizjacomn.algafood.api.v2.util;

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
public class AlgaLinksV2 extends AbstractAlgaLinks { }
