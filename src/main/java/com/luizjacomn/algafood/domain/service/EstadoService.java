package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.model.Estado;
import com.luizjacomn.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {

	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Estado informado não foi encontrado.";

	@Autowired
	private EstadoRepository estadoRepository;

	public Estado salvar(Estado estado, Long id) {
		if (id != null) {
			Estado estadoAtual = buscar(id);

			BeanUtils.copyProperties(estado, estadoAtual, "id");

			return estadoRepository.save(estadoAtual);
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

	public Estado buscar(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(MSG_ESTADO_NAO_ENCONTRADO));
	}
}
