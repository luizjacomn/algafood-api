package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.exceptionhandler.Problem;
import com.luizjacomn.algafood.api.model.mapper.CidadeMapper;
import com.luizjacomn.algafood.api.model.input.CidadeInput;
import com.luizjacomn.algafood.api.model.output.CidadeOutput;
import com.luizjacomn.algafood.domain.exception.EstadoNaoEncontradoException;
import com.luizjacomn.algafood.domain.exception.generics.NegocioException;
import com.luizjacomn.algafood.domain.model.Cidade;
import com.luizjacomn.algafood.domain.repository.CidadeRepository;
import com.luizjacomn.algafood.domain.service.CidadeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeMapper cidadeMapper;

    @ApiOperation("Listar todas as cidades")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade invélido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @GetMapping
    public List<CidadeOutput> listar() {
        return cidadeMapper.toOutputDTOList(cidadeRepository.findAll());
    }

    @ApiOperation("Buscar uma cidade através do ID")
    @GetMapping("/{id}")
    public CidadeOutput buscar(@ApiParam("ID de uma cidade") @PathVariable Long id) {
        return cidadeMapper.toOutputDTO(cidadeService.buscar(id));
    }

    @ApiOperation("Cadastrar uma nova cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade cadastrada"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeOutput salvar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
                                   @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeMapper.toEntity(cidadeInput);

            return cidadeMapper.toOutputDTO(cidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @ApiOperation("Atualizar uma cidade, informando seu ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @PutMapping("/{id}")
    public CidadeOutput atualizar(@ApiParam("ID de uma cidade") @PathVariable Long id,
                                  @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
                                  @RequestBody @Valid CidadeInput cidadeInput) throws Exception {
        try {
            Cidade cidade = cidadeService.buscar(id);
            cidadeMapper.copyToEntity(cidadeInput, cidade);

            return cidadeMapper.toOutputDTO(cidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @ApiOperation("Excluir uma cidade através do ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@ApiParam("ID de uma cidade") @PathVariable Long id) {
        cidadeService.excluir(id);
    }

}
