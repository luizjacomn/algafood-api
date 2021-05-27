package com.luizjacomn.algafood.api.openapi.controller;

import com.luizjacomn.algafood.api.filter.PedidoFilter;
import com.luizjacomn.algafood.api.model.output.PedidoOutput;
import com.luizjacomn.algafood.api.model.output.PedidoResumeOutput;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

public interface PedidoControllerOpenApi {

    @ApiImplicitParams({
            @ApiImplicitParam(name = "campos", paramType = "query", type = "string", example = "codigo,status",
                    value = "Nomes das propriedades para retornar na resposta, separados por vírgula")
    })
    Page<PedidoResumeOutput> pesquisar(PedidoFilter filter, Pageable pageable);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "campos", paramType = "query", type = "string", example = "codigo,status",
                    value = "Nomes das propriedades para retornar na resposta, separados por vírgula")
    })
    PedidoOutput buscar(@PathVariable String codigoPedido);
}
