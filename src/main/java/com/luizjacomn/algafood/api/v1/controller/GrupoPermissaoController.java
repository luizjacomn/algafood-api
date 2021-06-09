package com.luizjacomn.algafood.api.v1.controller;

import com.luizjacomn.algafood.api.v1.model.mapper.PermissaoMapper;
import com.luizjacomn.algafood.api.v1.model.output.PermissaoOutput;
import com.luizjacomn.algafood.domain.model.Grupo;
import com.luizjacomn.algafood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoMapper permissaoMapper;

    @GetMapping
    public CollectionModel<PermissaoOutput> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscar(grupoId);

        CollectionModel<PermissaoOutput> collectionModel = permissaoMapper.toCollectionModel(grupo.getPermissoes(), grupoId)
                .add(linkTo(methodOn(GrupoPermissaoController.class).adicionar(grupoId, null))
                        .withRel("adicionar"));

        collectionModel.getContent().forEach(perm -> {
            perm.add(linkTo(methodOn(GrupoPermissaoController.class).remover(grupoId, perm.getId())).withRel("remover"));
        });

        return collectionModel;
    }

    @PutMapping("/{permissaoId}")
    public ResponseEntity<Void> adicionar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.adicionarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<Void> remover(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.removerPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }
}
