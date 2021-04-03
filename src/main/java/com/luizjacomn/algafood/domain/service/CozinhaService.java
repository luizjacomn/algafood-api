package com.luizjacomn.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.model.Cozinha;
import com.luizjacomn.algafood.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	public void excluir(Long id) {
		Optional<Cozinha> optional = cozinhaRepository.findById(id);

		try {
			if (optional.isPresent()) {
				cozinhaRepository.deleteById(id);
			} else {
				throw new EntidadeNaoEncontradaException("Cozinha informada não encontrada.");
			}
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Cozinha está sendo utilizada e não pode ser excluída.");
		}
	}
}
