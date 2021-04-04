package com.luizjacomn.algafood.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.model.Restaurante;
import com.luizjacomn.algafood.domain.repository.RestauranteRepository;
import com.luizjacomn.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.luizjacomn.algafood.infra.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.luizjacomn.algafood.infra.repository.spec.RestauranteSpecs.comNomeSemelhante;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {
        return ResponseEntity.ok(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
        Optional<Restaurante> optional = restauranteRepository.findById(id);

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/por-nome-e-cozinha")
    public ResponseEntity<List<Restaurante>> listarPorNomeECozinha(String nome, @RequestParam("cozinha") Long cozinhaId) {
        return ResponseEntity.ok(restauranteRepository.listarPorNome(nome, cozinhaId));
    }

    @GetMapping("/quantidade-por-cozinha")
    public ResponseEntity<Integer> quantidadePorCozinha(@RequestParam("cozinha") Long cozinhaId) {
        return ResponseEntity.ok(restauranteRepository.countByCozinhaId(cozinhaId));
    }

    @GetMapping("/por-nome-e-frete-gratis")
    public ResponseEntity<List<Restaurante>> listarPorNomeEFreteGratis(String nome) {
        return ResponseEntity.ok(restauranteRepository.buscarComFreteGratis(nome));
    }

    @GetMapping("/por-nome-e-intervalo-taxas")
    public ResponseEntity<List<Restaurante>> listarPorNomeETaxas(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return ResponseEntity.ok(restauranteRepository.buscarPorNomeEIntervaloDeTaxas(nome, taxaInicial, taxaFinal));
    }

    @GetMapping("/por-intervalo-taxas")
    public ResponseEntity<List<Restaurante>> listarPorTaxas(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return ResponseEntity.ok(restauranteRepository.findByTaxaFreteBetweenOrderByTaxaFrete(taxaInicial, taxaFinal));
    }

    @PostMapping
    public ResponseEntity<Restaurante> salvar(@RequestBody Restaurante restaurante) {
        restaurante = restauranteService.salvar(restaurante, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        try {
            restaurante = restauranteService.salvar(restaurante, id);
            return ResponseEntity.ok(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> mesclar(@PathVariable Long id, @RequestBody Map<String, Object> dados) {
        Optional<Restaurante> optional = restauranteRepository.findById(id);

        if (optional.isPresent()) {
            ObjectMapper mapper = new ObjectMapper();
            Restaurante convertedValue = mapper.convertValue(dados, Restaurante.class);

            dados.keySet().forEach(chave -> {
                Field field = ReflectionUtils.findField(Restaurante.class, chave);
                field.setAccessible(true);

                Object valorAlterado = ReflectionUtils.getField(field, convertedValue);

                ReflectionUtils.setField(field, optional.get(), valorAlterado);
            });

            return atualizar(id, optional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            restauranteService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
