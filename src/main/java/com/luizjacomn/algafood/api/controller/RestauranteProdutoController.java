package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.input.ProdutoInput;
import com.luizjacomn.algafood.api.model.mapper.ProdutoMapper;
import com.luizjacomn.algafood.api.model.output.ProdutoOutput;
import com.luizjacomn.algafood.domain.model.Produto;
import com.luizjacomn.algafood.domain.model.Restaurante;
import com.luizjacomn.algafood.domain.service.ProdutoService;
import com.luizjacomn.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoMapper produtoMapper;

    @GetMapping
    public List<ProdutoOutput> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        return produtoMapper.toOutputDTOList(restaurante.getProdutos());
    }

    @GetMapping("/{produtoId}")
    public ProdutoOutput listar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = produtoService.buscar(produtoId, restauranteId);

        return produtoMapper.toOutputDTO(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoOutput salvar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        Produto produto = produtoMapper.toEntity(produtoInput);

        produto.setRestaurante(restaurante);

        return produtoMapper.toOutputDTO(produtoService.salvar(produto));
    }

    @PutMapping("/{produtoId}")
    public ProdutoOutput salvar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput) throws Exception {
        Produto produto = produtoService.buscar(produtoId, restauranteId);

        produtoMapper.copyToEntity(produtoInput, produto);

        return produtoMapper.toOutputDTO(produtoService.salvar(produto));
    }

    @DeleteMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        produtoService.excluir(produtoId, restauranteId);
    }

}
