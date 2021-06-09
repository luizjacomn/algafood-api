package com.luizjacomn.algafood.api.v2.controller;

import com.luizjacomn.algafood.api.v2.model.input.CozinhaInputV2;
import com.luizjacomn.algafood.api.v2.model.mapper.CozinhaMapperV2;
import com.luizjacomn.algafood.api.v2.model.output.CozinhaOutputV2;
import com.luizjacomn.algafood.api.v2.openapi.controller.CozinhaControllerOpenApiV2;
import com.luizjacomn.algafood.domain.model.Cozinha;
import com.luizjacomn.algafood.domain.repository.CozinhaRepository;
import com.luizjacomn.algafood.domain.service.CozinhaService;
import com.luizjacomn.algafood.util.MergeUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v2/cozinhas")
public class CozinhaControllerV2 implements CozinhaControllerOpenApiV2 {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaMapperV2 cozinhaMapperV2;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @Autowired
    private MergeUtil mergeUtil;

    @Override
    @GetMapping
    public PagedModel<CozinhaOutputV2> listar(Pageable pageable) {
        return pagedResourcesAssembler.toModel(cozinhaRepository.findAll(pageable), cozinhaMapperV2);
    }

    @Override
    @GetMapping("/{id}")
    public CozinhaOutputV2 buscar(@PathVariable Long id) {
        return cozinhaMapperV2.toModel(cozinhaService.buscar(id));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaOutputV2 salvar(@RequestBody @Valid CozinhaInputV2 cozinhaInputV2) {
        Cozinha cozinha = cozinhaMapperV2.toEntity(cozinhaInputV2);

        return cozinhaMapperV2.toModel(cozinhaService.salvar(cozinha));
    }

    @Override
    @PutMapping("/{id}")
    public CozinhaOutputV2 atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInputV2 cozinhaInputV2) throws Exception {
        Cozinha cozinha = cozinhaService.buscar(id);

        cozinhaMapperV2.copyToEntity(cozinhaInputV2, cozinha);

        return cozinhaMapperV2.toModel(cozinhaService.salvar(cozinha));
    }

    @Override
    @PatchMapping("/{id}")
    public CozinhaOutputV2 mesclar(@PathVariable Long id, @RequestBody Map<String, Object> dados, HttpServletRequest request) throws Exception {
        try {
            Cozinha cozinha = cozinhaService.buscar(id);

            CozinhaInputV2 cozinhaInputV2 = cozinhaMapperV2.toInputDTO(cozinha);

            mergeUtil.mergeMapIntoObject(dados, cozinhaInputV2, "cozinha");

            return atualizar(id, cozinhaInputV2);
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, new ServletServerHttpRequest(request));
        }
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        cozinhaService.excluir(id);
    }
}
