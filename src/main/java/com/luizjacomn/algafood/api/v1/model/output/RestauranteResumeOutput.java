package com.luizjacomn.algafood.api.v1.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteResumeOutput extends RepresentationModel<RestauranteResumeOutput> {

    private Long id;

    private String nome;
}
