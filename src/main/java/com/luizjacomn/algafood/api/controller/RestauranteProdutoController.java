package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.input.FotoProdutoInput;
import com.luizjacomn.algafood.api.model.input.ProdutoInput;
import com.luizjacomn.algafood.api.model.mapper.ProdutoMapper;
import com.luizjacomn.algafood.api.model.output.ProdutoOutput;
import com.luizjacomn.algafood.domain.model.Produto;
import com.luizjacomn.algafood.domain.model.Restaurante;
import com.luizjacomn.algafood.domain.repository.ProdutoRepository;
import com.luizjacomn.algafood.domain.service.ProdutoService;
import com.luizjacomn.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

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
    public void uploadFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {
        String nomeArquivoEntrada = fotoProdutoInput.getArquivo().getOriginalFilename();
        String nomeArquivo = UUID.randomUUID().toString() + nomeArquivoEntrada.substring(nomeArquivoEntrada.indexOf("."));

        Path path = Paths.get("C:\\Users\\ljaco\\Desktop", nomeArquivo);

        System.out.println(fotoProdutoInput.getDescricao());
        System.out.println(fotoProdutoInput.getArquivo().getContentType());
        System.out.println(path);

        try {
            fotoProdutoInput.getArquivo().transferTo(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
