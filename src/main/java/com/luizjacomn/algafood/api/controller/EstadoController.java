package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.converter.EstadoConverter;
import com.luizjacomn.algafood.api.model.input.EstadoInput;
import com.luizjacomn.algafood.api.model.output.EstadoOutput;
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

	@Autowired
	private EstadoConverter estadoConverter;
	
	@GetMapping
	public List<EstadoOutput> listar() {
		return estadoConverter.toOutputDTOList(estadoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public EstadoOutput buscar(@PathVariable Long id) {
		return estadoConverter.toOutputDTO(estadoService.buscar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoOutput salvar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoConverter.toEntity(estadoInput);

		return estadoConverter.toOutputDTO(estadoService.salvar(estado));
	}
	
	@PutMapping("{id}")
	public EstadoOutput atualizar(@RequestBody @Valid EstadoInput estadoInput, @PathVariable Long id) throws Exception {
		Estado estado = estadoService.buscar(id);

		estadoConverter.copyToEntity(estadoInput, estado);

		return estadoConverter.toOutputDTO(estadoService.salvar(estado));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		estadoService.excluir(id);
	}
}
