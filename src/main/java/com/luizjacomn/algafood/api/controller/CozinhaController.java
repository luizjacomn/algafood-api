package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.mapper.CozinhaMapper;
import com.luizjacomn.algafood.api.model.input.CozinhaInput;
import com.luizjacomn.algafood.api.model.output.CozinhaOutput;
import com.luizjacomn.algafood.domain.model.Cozinha;
import com.luizjacomn.algafood.domain.repository.CozinhaRepository;
import com.luizjacomn.algafood.domain.service.CozinhaService;
import com.luizjacomn.algafood.util.MergeUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaMapper cozinhaMapper;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @Autowired
    private MergeUtil mergeUtil;

    @GetMapping
    public PagedModel<CozinhaOutput> listar(Pageable pageable) {
        return pagedResourcesAssembler.toModel(cozinhaRepository.findAll(pageable), cozinhaMapper);
    }

    @GetMapping("/{id}")
    public CozinhaOutput buscar(@PathVariable Long id) {
        return cozinhaMapper.toModel(cozinhaService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaOutput salvar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaMapper.toEntity(cozinhaInput);

        return cozinhaMapper.toModel(cozinhaService.salvar(cozinha));
    }

    @PutMapping("/{id}")
    public CozinhaOutput atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) throws Exception {
        Cozinha cozinha = cozinhaService.buscar(id);

        cozinhaMapper.copyToEntity(cozinhaInput, cozinha);

        return cozinhaMapper.toModel(cozinhaService.salvar(cozinha));
    }

    @PatchMapping("/{id}")
    public CozinhaOutput mesclar(@PathVariable Long id, @RequestBody Map<String, Object> dados, HttpServletRequest request) throws Exception {
        try {
            Cozinha cozinha = cozinhaService.buscar(id);

            CozinhaInput cozinhaInput = cozinhaMapper.toInputDTO(cozinha);

            mergeUtil.mergeMapIntoObject(dados, cozinhaInput, "cozinha");

            return atualizar(id, cozinhaInput);
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, new ServletServerHttpRequest(request));
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        cozinhaService.excluir(id);
    }
}
