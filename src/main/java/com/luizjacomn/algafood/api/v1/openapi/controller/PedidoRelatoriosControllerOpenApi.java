package com.luizjacomn.algafood.api.v1.openapi.controller;

import com.luizjacomn.algafood.api.v1.controller.relatorios.PedidoRelatoriosController.RelatoriosModel;
import com.luizjacomn.algafood.api.v1.filter.VendaDiariaFilter;
import com.luizjacomn.algafood.api.v1.model.dto.VendaDiaria;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Estatísticas")
public interface PedidoRelatoriosControllerOpenApi {

    @ApiOperation(value = "Relatórios", hidden = true)
    RelatoriosModel relatorios();

    @ApiOperation(value = "Consulta estatísticas de vendas diárias")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restauranteId", value = "ID do restaurante",
                    example = "1", dataType = "int"),
            @ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora inicial da criação do pedido",
                    example = "2019-12-01T00:00:00Z", dataType = "date-time"),
            @ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora final da criação do pedido",
                    example = "2019-12-02T23:59:59Z", dataType = "date-time")
    })
    List<VendaDiaria> pesquisar(VendaDiariaFilter filter,
                                @ApiParam(value = "Deslocamento de horário a ser considerado na consulta em relação ao UTC",
                                        defaultValue = "+00:00") String timeOffset);

    ResponseEntity<byte[]> emitirPDF(VendaDiariaFilter filter, String timeOffset);
}
