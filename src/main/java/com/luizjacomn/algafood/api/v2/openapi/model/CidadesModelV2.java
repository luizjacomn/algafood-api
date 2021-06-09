package com.luizjacomn.algafood.api.v2.openapi.model;

import com.luizjacomn.algafood.api.v1.model.output.CidadeOutput;
import com.luizjacomn.algafood.api.v2.model.output.CidadeOutputV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesOutput")
@Data
public class CidadesModelV2 {

    private CidadeEmbedded _embedded;

    private Links _links;

    @ApiModel("CidadesEmbeddedOutput")
    @Data
    public class CidadeEmbedded {

        private List<CidadeOutputV2> cidades;

    }
}
