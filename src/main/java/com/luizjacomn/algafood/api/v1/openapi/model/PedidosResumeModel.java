package com.luizjacomn.algafood.api.v1.openapi.model;

import com.luizjacomn.algafood.api.openapi.model.PageModel;
import com.luizjacomn.algafood.api.v1.model.output.PedidoResumeOutput;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PedidosResumeOutput")
@Getter
@Setter
public class PedidosResumeModel {

    private PedidoResumeEmbedded _embedded;

    private Links _links;

    private PageModel page;

    @ApiModel("PedidosEmbeddedOutput")
    @Data
    public class PedidoResumeEmbedded {

        private List<PedidoResumeOutput> pedidos;

    }
}
