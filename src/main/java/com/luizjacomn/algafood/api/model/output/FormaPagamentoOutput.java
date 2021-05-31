package com.luizjacomn.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "formasPagamento")
@Getter
@Setter
public class FormaPagamentoOutput extends RepresentationModel<FormaPagamentoOutput> {

    private Long id;

    private String descricao;
}
