package com.luizjacomn.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.model.Cozinha;
import com.luizjacomn.algafood.domain.model.Restaurante;
import com.luizjacomn.algafood.domain.repository.CozinhaRepository;
import com.luizjacomn.algafood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Restaurante salvar(Restaurante restaurante, Long id) {
		Optional<Cozinha> cozinha = cozinhaRepository.findById(restaurante.getCozinha().getId());

		if (!cozinha.isPresent()) {
			throw new EntidadeNaoEncontradaException("Cozinha informada não foi encontrada.");
		}

		restaurante.setCozinha(cozinha.get());

		if (id != null) {
			Optional<Restaurante> restauranteAtual = restauranteRepository.findById(id);

			if (!restauranteAtual.isPresent()) {
				throw new EntidadeNaoEncontradaException("Restaurante informado não foi encontrado.");
			}

			BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");
			return restauranteRepository.save(restauranteAtual.get());
		}

		return restauranteRepository.save(restaurante);
	}

	public void excluir(Long id) {
		try {
			restauranteRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Restaurante informado não foi encontrado.");
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Restaurante está sendo utilizado e não pode ser excluído.");
		}
	}
}
