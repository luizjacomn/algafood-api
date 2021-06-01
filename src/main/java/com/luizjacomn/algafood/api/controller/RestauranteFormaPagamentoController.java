package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.mapper.FormaPagamentoMapper;
import com.luizjacomn.algafood.api.model.output.FormaPagamentoOutput;
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
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoMapper formaPagamentoMapper;

    @GetMapping
    public CollectionModel<FormaPagamentoOutput> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        CollectionModel<FormaPagamentoOutput> collectionModel = formaPagamentoMapper
                .toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(linkTo(RestauranteFormaPagamentoController.class, restauranteId).withRel(IanaLinkRelations.COLLECTION))
                .add(linkTo(methodOn(RestauranteFormaPagamentoController.class).associar(restauranteId, null))
                        .withRel("associar"));

        collectionModel.getContent().forEach(formaPag -> {
            formaPag.add(linkTo(methodOn(RestauranteFormaPagamentoController.class).desassociar(restauranteId, formaPag.getId()))
                    .withRel("desassociar"));
        });

        return collectionModel;
    }

    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

}
