package com.luizjacomn.algafood.api.v2.controller;

import com.luizjacomn.algafood.api.v2.model.input.CidadeInputV2;
import com.luizjacomn.algafood.api.v2.model.mapper.CidadeMapperV2;
import com.luizjacomn.algafood.api.v2.model.output.CidadeOutputV2;
import com.luizjacomn.algafood.api.v2.openapi.controller.CidadeControllerOpenApiV2;
import com.luizjacomn.algafood.domain.exception.EstadoNaoEncontradoException;
import com.luizjacomn.algafood.domain.exception.generics.NegocioException;
import com.luizjacomn.algafood.domain.model.Cidade;
import com.luizjacomn.algafood.domain.repository.CidadeRepository;
import com.luizjacomn.algafood.domain.service.CidadeService;
import com.luizjacomn.algafood.util.ResourceURIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/cidades")
public class CidadeControllerV2 implements CidadeControllerOpenApiV2 {

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeMapperV2 cidadeMapperV2;

    @Override
    @GetMapping
    public CollectionModel<CidadeOutputV2> listar() {
        return cidadeMapperV2.toCollectionModel(cidadeRepository.findAll());
    }

    @Override
    @GetMapping("/{id}")
    public CidadeOutputV2 buscar(@PathVariable Long id) {
        return cidadeMapperV2.toModel(cidadeService.buscar(id));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeOutputV2 salvar(@RequestBody @Valid CidadeInputV2 cidadeInputV2) {
        try {
            Cidade cidade = cidadeMapperV2.toEntity(cidadeInputV2);

            CidadeOutputV2 cidadeOutputV2 = cidadeMapperV2.toModel(cidadeService.salvar(cidade));

            ResourceURIUtil.addURIToResponse(cidadeOutputV2.getCidadeId());

            return cidadeOutputV2;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    @PutMapping("/{id}")
    public CidadeOutputV2 atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInputV2 cidadeInputV2) throws Exception {
        try {
            Cidade cidade = cidadeService.buscar(id);
            cidadeMapperV2.copyToEntity(cidadeInputV2, cidade);

            return cidadeMapperV2.toModel(cidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        cidadeService.excluir(id);
    }

}
