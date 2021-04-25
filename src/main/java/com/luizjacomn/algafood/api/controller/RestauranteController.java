package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.input.RestauranteInput;
import com.luizjacomn.algafood.api.model.mapper.RestauranteMapper;
import com.luizjacomn.algafood.api.model.output.RestauranteOutput;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.exception.generics.NegocioException;
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

    @Autowired
    private RestauranteMapper restauranteMapper;

    @Autowired
    private MergeUtil mergeUtil;

    @GetMapping
    public List<RestauranteOutput> listar() {
        return restauranteMapper.toOutputDTOList(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteOutput buscar(@PathVariable Long id) {
        return restauranteMapper.toOutputDTO(restauranteService.buscar(id));
    }

    @GetMapping("/por-nome-e-cozinha")
    public List<RestauranteOutput> listarPorNomeECozinha(String nome, @RequestParam("cozinha") Long cozinhaId) {
        return restauranteMapper.toOutputDTOList(restauranteRepository.listarPorNome(nome, cozinhaId));
    }

    @GetMapping("/quantidade-por-cozinha")
    public Integer quantidadePorCozinha(@RequestParam("cozinha") Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/por-nome-e-frete-gratis")
    public List<RestauranteOutput> listarPorNomeEFreteGratis(String nome) {
        return restauranteMapper.toOutputDTOList(restauranteRepository.buscarComFreteGratis(nome));
    }

    @GetMapping("/por-nome-e-intervalo-taxas")
    public List<RestauranteOutput> listarPorNomeETaxas(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteMapper.toOutputDTOList(restauranteRepository.buscarPorNomeEIntervaloDeTaxas(nome, taxaInicial, taxaFinal));
    }

    @GetMapping("/por-intervalo-taxas")
    public List<RestauranteOutput> listarPorTaxas(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteMapper.toOutputDTOList(restauranteRepository.findByTaxaFreteBetweenOrderByTaxaFrete(taxaInicial, taxaFinal));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteOutput salvar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteMapper.toEntity(restauranteInput);

            return restauranteMapper.toOutputDTO(restauranteService.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public RestauranteOutput atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) throws Exception {
        try {
            Restaurante restauranteAtual = restauranteService.buscar(id);

            restauranteMapper.copyToEntity(restauranteInput, restauranteAtual);

            return restauranteMapper.toOutputDTO(restauranteService.salvar(restauranteAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PatchMapping("/{id}")
    public RestauranteOutput mesclar(@PathVariable Long id, @RequestBody Map<String, Object> dados, HttpServletRequest request) throws Exception {
        try {
            RestauranteInput restauranteInput = restauranteMapper.toInputDTO(restauranteService.buscar(id));

            mergeUtil.mergeMapIntoObject(dados, restauranteInput, "restaurante");

            return atualizar(id, restauranteInput);
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

    @PutMapping("/{id}/ativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id) {
        restauranteService.ativar(id);
    }

    @DeleteMapping("/{id}/desativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativar(@PathVariable Long id) {
        restauranteService.desativar(id);
    }

    @PutMapping("/{id}/abrir")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long id) {
        restauranteService.abrir(id);
    }

    @PutMapping("/{id}/fechar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long id) {
        restauranteService.fechar(id);
    }

}
