package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.domain.model.Cidade;
import com.luizjacomn.algafood.domain.repository.CidadeRepository;
import com.luizjacomn.algafood.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Cidade buscar(@PathVariable Long id) {
		return cidadeService.buscar(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade salvar(@RequestBody Cidade cidade) {
			return cidadeService.salvar(cidade, null);
	}
	
	@PutMapping("/{id}")
	public Cidade salvar(@PathVariable Long id, @RequestBody Cidade cidade) {
		return cidadeService.salvar(cidade, id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		cidadeService.excluir(id);
	}
	
}
