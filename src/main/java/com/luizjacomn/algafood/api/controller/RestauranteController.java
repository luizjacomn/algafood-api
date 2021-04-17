package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.domain.exception.CidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.exception.NegocioException;
import com.luizjacomn.algafood.domain.model.Restaurante;
import com.luizjacomn.algafood.domain.repository.RestauranteRepository;
import com.luizjacomn.algafood.domain.service.RestauranteService;
import com.luizjacomn.algafood.util.MergeUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurante buscar(@PathVariable Long id) {
        return restauranteService.buscar(id);
    }

    @GetMapping("/por-nome-e-cozinha")
    public List<Restaurante> listarPorNomeECozinha(String nome, @RequestParam("cozinha") Long cozinhaId) {
        return restauranteRepository.listarPorNome(nome, cozinhaId);
    }

    @GetMapping("/quantidade-por-cozinha")
    public Integer quantidadePorCozinha(@RequestParam("cozinha") Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/por-nome-e-frete-gratis")
    public List<Restaurante> listarPorNomeEFreteGratis(String nome) {
        return restauranteRepository.buscarComFreteGratis(nome);
    }

    @GetMapping("/por-nome-e-intervalo-taxas")
    public List<Restaurante> listarPorNomeETaxas(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.buscarPorNomeEIntervaloDeTaxas(nome, taxaInicial, taxaFinal);
    }

    @GetMapping("/por-intervalo-taxas")
    public List<Restaurante> listarPorTaxas(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.findByTaxaFreteBetweenOrderByTaxaFrete(taxaInicial, taxaFinal);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante salvar(@RequestBody @Valid Restaurante restaurante) {
        try {
            return restauranteService.salvar(restaurante, null);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        try {
            return restauranteService.salvar(restaurante, id);
        } catch (CidadeNaoEncontradaException | CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public Restaurante mesclar(@PathVariable Long id, @RequestBody Map<String, Object> dados, HttpServletRequest request) {
        try {
            Restaurante restaurante = restauranteService.buscar(id);

            MergeUtil.mergeMapIntoObject(dados, restaurante);

            return atualizar(id, restaurante);
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, new ServletServerHttpRequest(request));
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        restauranteService.excluir(id);
    }

}
