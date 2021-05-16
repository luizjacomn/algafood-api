package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.input.FotoProdutoInput;
import com.luizjacomn.algafood.api.model.input.ProdutoInput;
import com.luizjacomn.algafood.api.model.mapper.FotoProdutoMapper;
import com.luizjacomn.algafood.api.model.mapper.ProdutoMapper;
import com.luizjacomn.algafood.api.model.output.FotoProdutoOutput;
import com.luizjacomn.algafood.api.model.output.ProdutoOutput;
import com.luizjacomn.algafood.domain.model.FotoProduto;
import com.luizjacomn.algafood.domain.model.Produto;
import com.luizjacomn.algafood.domain.model.Restaurante;
import com.luizjacomn.algafood.domain.repository.ProdutoRepository;
import com.luizjacomn.algafood.domain.service.FotoProdutoService;
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
    private FotoProdutoService fotoProdutoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private FotoProdutoMapper fotoProdutoMapper;

    @GetMapping
    public List<ProdutoOutput> listar(@PathVariable Long restauranteId,
                                      @RequestParam(value = "incluir-inativos", required = false) boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        List<Produto> produtos;
        if (incluirInativos) {
            produtos = produtoRepository.findByRestaurante(restaurante);
        } else {
            produtos = produtoRepository.findByRestauranteAndAtivoTrue(restaurante);
        }

        return produtoMapper.toOutputDTOList(produtos);
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

    @PutMapping("/{produtoId}/foto")
    public FotoProdutoOutput uploadFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {
        Produto produto = produtoService.buscar(produtoId, restauranteId);

        FotoProduto fotoProduto = fotoProdutoMapper.toEntity(fotoProdutoInput, produto);

        return fotoProdutoMapper.toOutputDTO(fotoProdutoService.salvar(fotoProduto));
    }

}
