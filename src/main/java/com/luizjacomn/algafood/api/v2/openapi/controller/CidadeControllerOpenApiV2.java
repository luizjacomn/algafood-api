package com.luizjacomn.algafood.api.v2.openapi.controller;

import com.luizjacomn.algafood.api.exceptionhandler.Problem;
import com.luizjacomn.algafood.api.v2.model.input.CidadeInputV2;
import com.luizjacomn.algafood.api.v2.model.output.CidadeOutputV2;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApiV2 {

    @ApiOperation("Listar todas as cidades")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CollectionModel<CidadeOutputV2> listar();

    @ApiOperation("Buscar uma cidade através do ID")
    CidadeOutputV2 buscar(@ApiParam("ID de uma cidade") Long id);

    @ApiOperation("Cadastrar uma nova cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade cadastrada"),
    })
    CidadeOutputV2 salvar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
                                  CidadeInputV2 cidadeInputV2);

    @ApiOperation("Atualizar uma cidade, informando seu ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeOutputV2 atualizar(@ApiParam("ID de uma cidade") Long id,
                             @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
                                     CidadeInputV2 cidadeInputV2) throws Exception;

    @ApiOperation("Excluir uma cidade através do ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    void excluir(@ApiParam("ID de uma cidade") Long id);
}
