package com.luizjacomn.algafood.api.v2.openapi.controller;

import com.luizjacomn.algafood.api.exceptionhandler.Problem;
import com.luizjacomn.algafood.api.v2.model.input.CozinhaInputV2;
import com.luizjacomn.algafood.api.v2.model.output.CozinhaOutputV2;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApiV2 {

    @ApiOperation("Listar todas as cozinhas")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    PagedModel<CozinhaOutputV2> listar(Pageable pageable);

    @ApiOperation("Buscar uma cozinha através do ID")
    CozinhaOutputV2 buscar(@ApiParam("ID de uma cozinha") Long id);

    @ApiOperation("Cadastrar uma nova cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrada"),
    })
    CozinhaOutputV2 salvar(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha")
                                   CozinhaInputV2 cozinhaInputV2);

    @ApiOperation("Atualizar uma cozinha, informando seu ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaOutputV2 atualizar(@ApiParam("ID de uma cozinha") Long id,
                              @ApiParam(name = "corpo", value = "Representação de uma nova cozinha") CozinhaInputV2 cozinhaInputV2) throws Exception;

    @ApiOperation("Atualizar parcialmente uma cozinha, informando seu ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaOutputV2 mesclar(@ApiParam("ID de uma cozinha") Long id,
                            @ApiParam(name = "corpo", value = "Representação de uma nova cozinha") Map<String, Object> dados, HttpServletRequest request) throws Exception;

    @ApiOperation("Excluir uma cozinha através do ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluída"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    void excluir(@ApiParam("ID de uma cozinha") Long id);
}
