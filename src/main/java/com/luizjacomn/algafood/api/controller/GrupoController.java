package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.input.GrupoInput;
import com.luizjacomn.algafood.api.model.mapper.GrupoMapper;
import com.luizjacomn.algafood.api.model.output.GrupoOutput;
import com.luizjacomn.algafood.domain.model.Grupo;
import com.luizjacomn.algafood.domain.repository.GrupoRepository;
import com.luizjacomn.algafood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private GrupoMapper grupoMapper;
	
	@GetMapping
	public CollectionModel<GrupoOutput> listar() {
		return grupoMapper.toCollectionModel(grupoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public GrupoOutput buscar(@PathVariable Long id) {
		return grupoMapper.toModel(grupoService.buscar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoOutput salvar(@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = grupoMapper.toEntity(grupoInput);

		return grupoMapper.toModel(grupoService.salvar(grupo));
	}
	
	@PutMapping("/{id}")
	public GrupoOutput atualizar(@RequestBody @Valid GrupoInput grupoInput, @PathVariable Long id) throws Exception {
		Grupo grupo = grupoService.buscar(id);

		grupoMapper.copyToEntity(grupoInput, grupo);

		return grupoMapper.toModel(grupoService.salvar(grupo));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		grupoService.excluir(id);
	}
}
