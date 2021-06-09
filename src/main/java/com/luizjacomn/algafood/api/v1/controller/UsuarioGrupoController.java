package com.luizjacomn.algafood.api.v1.controller;

import com.luizjacomn.algafood.api.v1.model.mapper.GrupoMapper;
import com.luizjacomn.algafood.api.v1.model.output.GrupoOutput;
import com.luizjacomn.algafood.domain.model.Usuario;
import com.luizjacomn.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoMapper grupoMapper;

    @GetMapping
    public CollectionModel<GrupoOutput> listar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscar(usuarioId);

        CollectionModel<GrupoOutput> collectionModel = grupoMapper.toCollectionModel(usuario.getGrupos());

        collectionModel.removeLinks();

        collectionModel.add(linkTo(methodOn(UsuarioGrupoController.class).listar(usuarioId)).withRel(IanaLinkRelations.COLLECTION));

        collectionModel.add(linkTo(methodOn(UsuarioGrupoController.class).adicionar(usuarioId, null)).withRel("adicionar"));

        collectionModel.getContent().forEach(grupo -> {
            grupo.add(linkTo(methodOn(UsuarioGrupoController.class).remover(usuarioId, grupo.getId())).withRel("remover"));
        });

        return collectionModel;
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> adicionar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.adicionarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.removerGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }
}
