package com.luizjacomn.algafood.api.v2.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@ApiModel("CidadeOutput")
@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeOutputV2 extends RepresentationModel<CidadeOutputV2> {

    @ApiModelProperty(example = "1")
    private Long cidadeId;

    @ApiModelProperty(example = "Fortaleza")
    private String cidadeNome;

    @ApiModelProperty(example = "1")
    private Long estadoId;

    @ApiModelProperty(example = "Ceará")
    private String estadoNome;

}
