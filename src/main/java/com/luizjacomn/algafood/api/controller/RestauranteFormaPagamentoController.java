package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.mapper.FormaPagamentoMapper;
import com.luizjacomn.algafood.api.model.output.FormaPagamentoOutput;
import com.luizjacomn.algafood.domain.model.Restaurante;
import com.luizjacomn.algafood.domain.repository.FormaPagamentoRepository;
import com.luizjacomn.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoMapper formaPagamentoMapper;

    @GetMapping
    public List<FormaPagamentoOutput> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        return formaPagamentoMapper.toOutputDTOList(restaurante.getFormasPagamento());
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.associar(restauranteId, formaPagamentoId);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociar(restauranteId, formaPagamentoId);
    }

}
