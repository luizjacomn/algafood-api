package com.luizjacomn.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "usuarios")
@Getter
@Setter
public class UsuarioOutput extends RepresentationModel<UsuarioOutput> {

    private Long id;

    private String nome;

    private String email;
}
