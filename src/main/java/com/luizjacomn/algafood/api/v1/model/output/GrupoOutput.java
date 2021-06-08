package com.luizjacomn.algafood.api.v1.model.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "grupos")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class GrupoOutput extends RepresentationModel<GrupoOutput> {

    private Long id;

    private String nome;
}
