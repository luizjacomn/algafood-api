package com.luizjacomn.algafood.api.openapi.model;

import com.luizjacomn.algafood.api.model.output.CidadeOutput;
import com.luizjacomn.algafood.api.model.output.CozinhaOutput;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasOutput")
@Getter
@Setter
public class CozinhasModel {

    private CozinhaEmbedded _embedded;

    private Links _links;

    private PageModel page;

    @ApiModel("CozinhasEmbeddedOutput")
    @Data
    public class CozinhaEmbedded {

        private List<CozinhaOutput> cozinhas;

    }
}
