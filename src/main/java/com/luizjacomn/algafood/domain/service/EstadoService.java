package com.luizjacomn.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.model.Estado;
import com.luizjacomn.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public Estado salvar(Estado estado, Long id) {
		if (id != null) {
			Optional<Estado> optional = estadoRepository.findById(id);

			if (!optional.isPresent()) {
				throw new EntidadeNaoEncontradaException("Estado informado não encontrado.");
			}

			BeanUtils.copyProperties(estado, optional.get(), "id");
			return estadoRepository.save(optional.get());
		}

		return estadoRepository.save(estado);
	}

	public void excluir(Long id) {
		try {
			estadoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Estado informado não foi encontrado.");
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Estado está sendo utilizado e não pode ser excluído.");
		}
	}
}
