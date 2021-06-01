package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.mapper.UsuarioMapper;
import com.luizjacomn.algafood.api.model.output.UsuarioOutput;
import com.luizjacomn.algafood.domain.model.Restaurante;
import com.luizjacomn.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteResponsavelController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @GetMapping
    public CollectionModel<UsuarioOutput> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        CollectionModel<UsuarioOutput> collectionModel = usuarioMapper.toCollectionModel(restaurante.getResponsaveis())
                .removeLinks()
                .add(linkTo(RestauranteResponsavelController.class, restauranteId).withRel(IanaLinkRelations.COLLECTION))
                .add(linkTo(methodOn(RestauranteResponsavelController.class).associar(restauranteId, null))
                        .withRel("associar"));

        collectionModel.getContent().forEach(resp -> {
            resp.add(linkTo(methodOn(RestauranteResponsavelController.class).desassociar(restauranteId, resp.getId()))
                    .withRel("desassociar"));
        });

        return collectionModel;
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.associarResponsavel(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.desassociarResponsavel(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }

}
