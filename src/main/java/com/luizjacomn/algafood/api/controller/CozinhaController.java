package com.luizjacomn.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizjacomn.algafood.domain.model.Restaurante;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.model.Cozinha;
import com.luizjacomn.algafood.domain.repository.CozinhaRepository;
import com.luizjacomn.algafood.domain.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Cozinha> salvar(@RequestBody Cozinha cozinha) {
		return ResponseEntity.status(HttpStatus.CREATED)
							.body(cozinhaService.salvar(cozinha));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		Optional<Cozinha> optional = cozinhaRepository.findById(id);
		
		if (optional.isPresent()) {
			BeanUtils.copyProperties(cozinha, optional.get(), "id");
			return ResponseEntity.ok(cozinhaService.salvar(optional.get()));
		}
		
		return ResponseEntity.notFound().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> mesclar(@PathVariable Long id, @RequestBody Map<String, Object> dados) {
		Optional<Cozinha> optional = cozinhaRepository.findById(id);

		if (optional.isPresent()) {
			ObjectMapper mapper = new ObjectMapper();
			Cozinha convertedValue = mapper.convertValue(dados, Cozinha.class);

			dados.keySet().forEach(chave -> {
				Field field = ReflectionUtils.findField(Cozinha.class, chave);
				field.setAccessible(true);

				Object valorAlterado = ReflectionUtils.getField(field, convertedValue);

				ReflectionUtils.setField(field, optional.get(), valorAlterado);
			});

			return atualizar(id, optional.get());
		}

		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cozinha> excluir(@PathVariable Long id) {
		try {
			cozinhaService.excluir(id);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
