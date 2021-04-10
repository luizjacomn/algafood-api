package com.luizjacomn.algafood.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizjacomn.algafood.domain.model.Cozinha;
import com.luizjacomn.algafood.domain.repository.CozinhaRepository;
import com.luizjacomn.algafood.domain.service.CozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha salvar(@RequestBody Cozinha cozinha) {
		return cozinhaService.salvar(cozinha);
	}
	
	@PutMapping("/{id}")
	public Cozinha atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = cozinhaService.buscar(id);
		
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

		return cozinhaService.salvar(cozinhaAtual);
	}

	@PatchMapping("/{id}")
	public Cozinha mesclar(@PathVariable Long id, @RequestBody Map<String, Object> dados) {
		Cozinha cozinha = cozinhaService.buscar(id);

		ObjectMapper mapper = new ObjectMapper();
		Cozinha convertedValue = mapper.convertValue(dados, Cozinha.class);

		dados.keySet().forEach(chave -> {
			Field field = ReflectionUtils.findField(Cozinha.class, chave);
			field.setAccessible(true);

			Object valorAlterado = ReflectionUtils.getField(field, convertedValue);

			ReflectionUtils.setField(field, cozinha, valorAlterado);
		});

		return atualizar(id, cozinha);

	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		cozinhaService.excluir(id);
	}
}
