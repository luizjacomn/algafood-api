package com.luizjacomn.algafood.api.v1.openapi.controller;

import com.luizjacomn.algafood.api.v1.filter.PedidoFilter;
import com.luizjacomn.algafood.api.v1.model.output.PedidoOutput;
import com.luizjacomn.algafood.api.v1.model.output.PedidoResumeOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiImplicitParams({
            @ApiImplicitParam(name = "campos", paramType = "query", type = "string", example = "codigo,status",
                    value = "Nomes das propriedades para retornar na resposta, separados por vírgula")
    })
    PagedModel<PedidoResumeOutput> pesquisar(PedidoFilter filter, Pageable pageable);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "campos", paramType = "query", type = "string", example = "codigo,status",
                    value = "Nomes das propriedades para retornar na resposta, separados por vírgula")
    })
    PedidoOutput buscar(@PathVariable String codigoPedido);
}
