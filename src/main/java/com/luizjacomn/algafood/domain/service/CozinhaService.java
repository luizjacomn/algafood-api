package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.model.Cozinha;
import com.luizjacomn.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CozinhaService {

	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Cozinha informada não encontrada.";
	private static final String MSG_COZINHA_EM_USO = "Cozinha está sendo utilizada e não pode ser excluída.";

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	public void excluir(Long id) {
		try {
			cozinhaRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(MSG_COZINHA_NAO_ENCONTRADA);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MSG_COZINHA_EM_USO);
		}
	}

	public Cozinha buscar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(MSG_COZINHA_NAO_ENCONTRADA));
	}
}
