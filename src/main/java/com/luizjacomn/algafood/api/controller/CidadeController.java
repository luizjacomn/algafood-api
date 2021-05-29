package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.input.CidadeInput;
import com.luizjacomn.algafood.api.model.mapper.CidadeMapper;
import com.luizjacomn.algafood.api.model.output.CidadeOutput;
import com.luizjacomn.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.luizjacomn.algafood.domain.exception.EstadoNaoEncontradoException;
import com.luizjacomn.algafood.domain.exception.generics.NegocioException;
import com.luizjacomn.algafood.domain.model.Cidade;
import com.luizjacomn.algafood.domain.repository.CidadeRepository;
import com.luizjacomn.algafood.domain.service.CidadeService;
import com.luizjacomn.algafood.util.ResourceURIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "/cidades")
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeMapper cidadeMapper;

    @Override
    @GetMapping
    public CollectionModel<CidadeOutput> listar() {
        List<CidadeOutput> cidadesOutput = cidadeMapper.toOutputDTOList(cidadeRepository.findAll());

        cidadesOutput.forEach(item -> {
            item.add(linkTo(CidadeController.class).slash(item.getId()).withSelfRel());

            item.getEstado().add(linkTo(EstadoController.class).slash(item.getEstado().getId()).withSelfRel());

            item.getEstado().add(linkTo(EstadoController.class).withRel(IanaLinkRelations.COLLECTION));
        });

        CollectionModel<CidadeOutput> collectionModel = CollectionModel.of(cidadesOutput);

        collectionModel.add(linkTo(CidadeController.class).withRel(IanaLinkRelations.COLLECTION));

        return collectionModel;
    }

    @Override
    @GetMapping("/{id}")
    public CidadeOutput buscar(@PathVariable Long id) {
        CidadeOutput cidadeOutput = cidadeMapper.toOutputDTO(cidadeService.buscar(id));

        cidadeOutput.add(linkTo(CidadeController.class).slash(cidadeOutput.getId()).withSelfRel());

        cidadeOutput.add(linkTo(CidadeController.class).withRel(IanaLinkRelations.COLLECTION));

        cidadeOutput.getEstado().add(linkTo(EstadoController.class).slash(cidadeOutput.getEstado().getId()).withSelfRel());

        cidadeOutput.getEstado().add(linkTo(EstadoController.class).withRel(IanaLinkRelations.COLLECTION));

        return cidadeOutput;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeOutput salvar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeMapper.toEntity(cidadeInput);

            CidadeOutput cidadeOutput = cidadeMapper.toOutputDTO(cidadeService.salvar(cidade));

            ResourceURIUtil.addURIToResponse(cidadeOutput.getId());

            return cidadeOutput;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    @PutMapping("/{id}")
    public CidadeOutput atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) throws Exception {
        try {
            Cidade cidade = cidadeService.buscar(id);
            cidadeMapper.copyToEntity(cidadeInput, cidade);

            return cidadeMapper.toOutputDTO(cidadeService.salvar(cidade));
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
