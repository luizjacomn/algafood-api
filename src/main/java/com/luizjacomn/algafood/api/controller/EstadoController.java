package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.domain.model.Estado;
import com.luizjacomn.algafood.domain.repository.EstadoRepository;
import com.luizjacomn.algafood.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoService estadoService;

	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Estado buscar(@PathVariable Long id) {
		return estadoService.buscar(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado salvar(@RequestBody @Valid Estado estado) {
		return estadoService.salvar(estado, null);
	}
	
	@PutMapping("{id}")
	public Estado atualizar(@RequestBody @Valid Estado estado, @PathVariable Long id) {
		return estadoService.salvar(estado, id);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		estadoService.excluir(id);
	}
}
