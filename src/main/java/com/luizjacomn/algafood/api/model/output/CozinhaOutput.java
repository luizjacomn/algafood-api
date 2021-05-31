package com.luizjacomn.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaOutput extends RepresentationModel<CozinhaOutput> {

    private Long id;

    private String nome;
}
