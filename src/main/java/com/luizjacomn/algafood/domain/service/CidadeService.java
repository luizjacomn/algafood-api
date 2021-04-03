package com.luizjacomn.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.model.Estado;
import com.luizjacomn.algafood.domain.model.Cidade;
import com.luizjacomn.algafood.domain.repository.EstadoRepository;
import com.luizjacomn.algafood.domain.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cidade salvar(Cidade cidade, Long id) {
		Optional<Estado> estado = estadoRepository.findById(cidade.getEstado().getId());
		
		if (!estado.isPresent()) {
			throw new EntidadeNaoEncontradaException("Estado informado não foi encontrado.");
		}
		
		cidade.setEstado(estado.get());
		
		if (id != null) {
			Optional<Cidade> cidadeAtual = cidadeRepository.findById(id);
			
			if (!cidadeAtual.isPresent()) {
				throw new EntidadeNaoEncontradaException("Cidade informada não foi encontrada.");
			}
			
			BeanUtils.copyProperties(cidade, cidadeAtual.get(), "id");
			return cidadeRepository.save(cidadeAtual.get());
		}
		
		return cidadeRepository.save(cidade);
	}
	
	public void excluir(Long id) {
		try {
			Optional<Cidade> optional = cidadeRepository.findById(id);
			if (optional.isPresent()) {
				cidadeRepository.deleteById(id);
			} else {
				throw new EntidadeNaoEncontradaException("Cidade informada não foi encontrada.");
			}
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Cidade está sendo utilizada e não pode ser excluída.");
		}
	}
}
