package com.luizjacomn.algafood.api.v2.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaOutputV2 extends RepresentationModel<CozinhaOutputV2> {

    private Long cozinhaId;

    private String cozinhaNome;
}
