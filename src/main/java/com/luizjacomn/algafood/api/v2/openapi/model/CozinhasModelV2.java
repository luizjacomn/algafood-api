package com.luizjacomn.algafood.api.v2.openapi.model;

import com.luizjacomn.algafood.api.openapi.model.PageModel;
import com.luizjacomn.algafood.api.v1.model.output.CozinhaOutput;
import com.luizjacomn.algafood.api.v2.model.output.CozinhaOutputV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasOutput")
@Getter
@Setter
public class CozinhasModelV2 {

    private CozinhaEmbedded _embedded;

    private Links _links;

    private PageModel page;

    @ApiModel("CozinhasEmbeddedOutput")
    @Data
    public class CozinhaEmbedded {

        private List<CozinhaOutputV2> cozinhas;

    }
}
