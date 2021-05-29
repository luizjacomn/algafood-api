package com.luizjacomn.algafood.api.openapi.controller;

import com.luizjacomn.algafood.api.exceptionhandler.Problem;
import com.luizjacomn.algafood.api.model.input.FotoProdutoInput;
import com.luizjacomn.algafood.api.model.output.FotoProdutoOutput;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

public interface FotoProdutoControllerOpenApi {

    @ApiOperation("Atualizar a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Foto do produto atualizada"),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
    })
    FotoProdutoOutput uploadFoto(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                                 @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId,
                                 FotoProdutoInput fotoProdutoInput) throws IOException;

    @ApiOperation("Excluir a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Foto do produto excluída"),
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto do produto não encontrada", response = Problem.class)
    })
    void excluirFoto(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                     @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId);

    @ApiOperation(value = "Buscar a foto do produto de um restaurante", produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class)
    })
    FotoProdutoOutput recuperarDadosFoto(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                                         @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId);

    @ApiOperation(value = "Buscar a foto do produto de um restaurante", hidden = true)
    ResponseEntity<InputStreamResource> recuperarFoto(Long restauranteId, Long produtoId, String accept) throws HttpMediaTypeNotAcceptableException;
}
