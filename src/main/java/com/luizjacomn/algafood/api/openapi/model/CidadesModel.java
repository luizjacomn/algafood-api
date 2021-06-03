package com.luizjacomn.algafood.api.openapi.model;

import com.luizjacomn.algafood.api.model.output.CidadeOutput;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesOutput")
@Data
public class CidadesModel {

    private CidadeEmbedded _embedded;

    private Links _links;

    @ApiModel("CidadesEmbeddedOutput")
    @Data
    public class CidadeEmbedded {

        private List<CidadeOutput> cidades;

    }
}
