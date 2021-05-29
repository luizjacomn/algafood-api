package com.luizjacomn.algafood.api.openapi.controller;

import com.luizjacomn.algafood.api.exceptionhandler.Problem;
import com.luizjacomn.algafood.api.model.input.CidadeInput;
import com.luizjacomn.algafood.api.model.output.CidadeOutput;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Listar todas as cidades")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    List<CidadeOutput> listar();

    @ApiOperation("Buscar uma cidade através do ID")
    CidadeOutput buscar(@ApiParam("ID de uma cidade") Long id);

    @ApiOperation("Cadastrar uma nova cidade")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Cidade cadastrada"),
    })
    CidadeOutput salvar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
                                CidadeInput cidadeInput);

    @ApiOperation("Atualizar uma cidade, informando seu ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cidade atualizada"),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeOutput atualizar(@ApiParam("ID de uma cidade") Long id,
                           @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
                                   CidadeInput cidadeInput) throws Exception;

    @ApiOperation("Excluir uma cidade através do ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Cidade excluída"),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    void excluir(@ApiParam("ID de uma cidade") Long id);
}
