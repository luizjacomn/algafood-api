package com.luizjacomn.algafood.api.v1.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "permissoes")
@Getter
@Setter
public class PermissaoOutput extends RepresentationModel<PermissaoOutput> {

    private Long id;

    private String nome;

    private String descricao;

}
