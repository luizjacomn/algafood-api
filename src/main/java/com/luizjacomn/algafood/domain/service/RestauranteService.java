package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.model.Cidade;
import com.luizjacomn.algafood.domain.repository.CidadeRepository;
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

	@Autowired
	private CidadeRepository cidadeRepository;

	public Restaurante salvar(Restaurante restaurante, Long id) {
		Cozinha cozinha = cozinhaRepository.findById(restaurante.getCozinha().getId())
											.orElseThrow(() -> new EntidadeNaoEncontradaException("Cozinha informada não foi encontrada."));

		restaurante.setCozinha(cozinha);

		if (restaurante.getEndereco() != null && restaurante.getEndereco().getCidade().getId() != null) {
			Cidade cidade = cidadeRepository.findById(restaurante.getEndereco().getCidade().getId())
					.orElseThrow(() -> new EntidadeNaoEncontradaException("Cidade informada não foi encontrada."));

			restaurante.getEndereco().setCidade(cidade);
		}

		if (id != null) {
			Restaurante restauranteAtual = restauranteRepository.findById(id)
																.orElseThrow(() -> new EntidadeNaoEncontradaException("Restaurante informado não foi encontrado."));

			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "endereco", "formasPagamento", "dataCadastro");
			return restauranteRepository.save(restauranteAtual);
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
